package com.project.ohgym.controller;

import com.project.ohgym.dto.GymDto;
import com.project.ohgym.dto.MemberDto;
import com.project.ohgym.service.GymService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@Slf4j
public class GymController {

    @Autowired
    private GymService gServ;
    private ModelAndView mv;


    // 헬스장 목록
    @GetMapping("hGymMList")
    public ModelAndView hGymMList(HttpSession session) {
        log.info("hGymMList()");
        mv = gServ.hGymMList(session);
        return mv;
    }

    // 헬스장 목록
    @GetMapping("hGymList")
    public ModelAndView hGymList() {
        log.info("hGymList()");
        mv = gServ.hGymList();
        return mv;
    }

    // 검색 필터
    @GetMapping("gymSetting")
    public String gymSetting() {
        log.info("gymSetting()");
        return "gymSetting";
    }

    // 헬스장 셀렉트 검색 첫번째 목록 받아오기
    @PostMapping("gymList")
    @ResponseBody
    public List<GymDto> hGymPlaceList(String glocation){
        log.info("hGymPlaceList()");
        List<GymDto> addrList = gServ.hGymPlaceList(glocation);
        return addrList;
    }

    // 헬스장 셀렉트 검색 두번째 목록 받아오기
    @GetMapping("gymList")
    @ResponseBody
    public List<GymDto> hSeachGymOutput(String gcity){
        log.info("hSeachGymOutput()");
        List<GymDto> cityList = gServ.hSeachGymOutput(gcity);
        return cityList;
    }

    //헬스장 키워드로 검색
    @GetMapping("hSearch")
    @ResponseBody
    public ModelAndView hSearch(String gname){
        log.info("hSearch()");
        mv = gServ.hSearch(gname);
        return mv;
    }

    // 검색 필터 목록
    @PostMapping("hGymFilterList")
    public ModelAndView hGymFilterList(String ggoodsname, String mgender, @RequestParam("used")List<String> check) {
        log.info("hGymFilterList()");
        mv = gServ.hGymFilterList(ggoodsname, mgender, check);
        return mv;
    }

    // 헬스장 상세 페이지
    @GetMapping("gymPage")
    public ModelAndView gymPage(Integer gymnum, HttpSession session){
        log.info("gymPage()");
        if (session.getAttribute("member") == null){
            mv = gServ.getNotGymPage(gymnum);
            mv.setViewName("gymPageNM");
        } else {
            MemberDto member = (MemberDto) session.getAttribute("member");
            Integer membernum = member.getMembernum();
            mv = gServ.getGymPage(gymnum, membernum);
            mv.setViewName("gymPage");
        }
        return mv;
    }

    // 찜 등록
    @PostMapping("inputMark")
    @ResponseBody
    public String inputMark(Integer membernum, Integer gymnum){
        log.info("inputMark()");
        String res = gServ.inputMark(membernum, gymnum);
        return res;
    }
    // 찜 삭제
    @PostMapping("delMark")
    @ResponseBody
    public String delMark(Integer membernum, Integer gymnum){
        log.info("delMark()");
        String res = gServ.delMark(membernum, gymnum);
        return res;
    }

    //캘린더
    @GetMapping("calendar")
    public String calendar(){
        log.info("calendar()");
        return "calendar";
    }

}
