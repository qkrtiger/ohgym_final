package com.project.ohgym.controller;

import com.project.ohgym.dto.MemberDto;
import com.project.ohgym.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@Slf4j
public class MemberController {
    @Autowired
    private MemberService mServ;

    private ModelAndView mv;

    @GetMapping("/")
    public String home(){
        log.info("home()");

        return "home";
    }
    //회원 마이페이지
    @GetMapping("memberMypage")
    public ModelAndView mInfo(Integer membernum, HttpSession session){
        log.info("mInfo()");

        mv = mServ.getmInfo(membernum, session);
        return mv;
    }

    //찜목록
    @GetMapping("wishList")
    public ModelAndView mMarkOutput(Integer membernum) {
        log.info("mMarkOutput()");
        mv = mServ.mMarkGym(membernum);

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



}
