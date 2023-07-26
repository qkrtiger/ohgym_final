package com.project.ohgym.controller;

import com.project.ohgym.service.MainService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Slf4j
public class MainController {
    @Autowired
    private MainService miServ;

    private ModelAndView mv;

    @GetMapping("/")
    public ModelAndView main(HttpSession session){
        log.info("main()");

        //게시글 출력 & 헬스장 출력
        mv = miServ.bestBoard();

        return mv;
    }

    // 지역 헬스장 검색
    @GetMapping("gymPlaceList")
    public ModelAndView gymPlaceList(String glocation) {
        log.info("gymPlaceList()");
        mv = miServ.gymPlaceList(glocation);
        return mv;
    }

    // 헬스장 검색어 검색
    @GetMapping("SeachGymOutput")
    public ModelAndView searchList(String gname) {
        log.info("searchList()");
        mv = miServ.searchList(gname);
        return mv;
    }

    // 일일권 검색
    @GetMapping("onelist")
    public ModelAndView onelist(String goods) {
        log.info("onelist()");
        mv = miServ.oneList(goods);
        mv.setViewName("gymList");
        return mv;
    }

    // 여자 트레이너 목록 받아오기
    @GetMapping("womanlist")
    public ModelAndView womanList(String wgender) {
        log.info("womanList()");
        mv = miServ.womanList(wgender);
        mv.setViewName("gymList");
        return mv;
    }

    // 남자 트레이너 목록 받아오기
    @GetMapping("manlist")
    public ModelAndView manList(String mgender) {
        log.info("manList()");
        mv = miServ.manList(mgender);
        mv.setViewName("gymList");
        return mv;
    }

    @GetMapping("terms")
    public String terms(){
        log.info("terms()");
        return "terms";
    }

    @GetMapping("faq")
    public String faq(){
        log.info("faq()");
        return "faq";
    }

    @GetMapping("introduce")
    public String introduce(){
        log.info("introduce()");
        return "introduce";
    }

    @GetMapping("guide")
    public String guide(){
        log.info("guide()");
        return "guide";
    }

}
