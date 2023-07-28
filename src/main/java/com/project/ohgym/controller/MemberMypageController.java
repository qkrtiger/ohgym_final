package com.project.ohgym.controller;

import com.project.ohgym.dto.MemberDto;
import com.project.ohgym.dto.SearchDto;
import com.project.ohgym.service.MemberMypageService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

@Controller
@Slf4j
public class MemberMypageController {
    @Autowired
    private MemberMypageService mServ;

    private ModelAndView mv;


    //회원 마이페이지
    @GetMapping("memberMypage")
    public ModelAndView mInfo(Integer membernum, HttpSession session){
        log.info("mInfo()");

        mv = mServ.getmInfo(membernum, session);
        return mv;
    }

    //팝업창(이미지수정)
    @GetMapping("image")
    public ModelAndView image(Integer membernum){
        log.info("image()");

        mv = mServ.getProfile(membernum);

        return mv;
    }

    //이미지수정
    @PostMapping("mImage")
    public String mImage(@RequestPart(required = false) List<MultipartFile> files, MemberDto member, HttpSession session, RedirectAttributes rttr) {
        log.info("mImage()");
        String view = mServ.insertImage(files, member, session, rttr);
        return view;
    }

    //찜목록
    @GetMapping("wishList")
    public ModelAndView mMarkOutput(Integer membernum) {
        log.info("mMarkOutput()");
        mv = mServ.mMarkGym(membernum);

        return mv;
    }

    @GetMapping("mProfile")
    public ModelAndView mProfile(Integer membernum){
        log.info("mProfile()");

        mv = mServ.getProfile(membernum);
        mv.setViewName("mProfile");
        return mv;
    }

    //팝업창(트레이너 등록)
    @GetMapping("mTrainer")
    public ModelAndView mTrainer(Integer membernum){
        log.info("mTrainer()");

        mv = mServ.getProfile(membernum);
        mv.setViewName("mTrainer");

        return mv;
    }

    // 헬스장 고유번호 확인
    @PostMapping("numCheck")
    @ResponseBody           //REST 방식일 때 꼭 넣을 것.
    public String numCheck(int gymnum){
        log.info("numCheck()");
        String res = mServ.numCheck(gymnum);
        return res;
    }

    @PostMapping("Change")
    public String Change(Integer gymnum, MemberDto member, HttpSession session,
                         RedirectAttributes rttr) {
        log.info("Change()");

        String view = mServ.tChange(gymnum, member, session, rttr);

        return view;
    }


    @GetMapping("memberInfo")
    public ModelAndView memberInfo(Integer membernum, HttpSession session){
        log.info("memberInfo()");

        mv = mServ.getMemberInfo(membernum, session);

        return mv;
    }
    @PostMapping("mtInfo")
    public String mtInfo(MemberDto member,
                         HttpSession session,
                         RedirectAttributes rttr){
        log.info("mtInfo()");

        String view = mServ.updateMember(member, session, rttr);

        return view;
    }

    //결제 내역 가져오기
    @GetMapping("myPayList")
    public ModelAndView mPay(SearchDto search, HttpSession session){
        log.info("myPayList()");

        mv = mServ.mPay(search,session);
        return mv;
    }

    //결제내역 조회(회원권, 일일권, PT)
    @PostMapping("myPayList")
    @ResponseBody
    public Map<String, Object> selectedMpay(@RequestBody SearchDto search){
        log.info("selectedMpay()");
        //String mpayType = (String)map.get("mpayType");
        //int membernum = (Integer)map.get("membernum");

        //List<MPayDto> mPList = mServ.selectedMpay(mpayType, membernum);
        Map<String, Object> rmap = mServ.selectedMpay(search);

        return rmap;
    }
}