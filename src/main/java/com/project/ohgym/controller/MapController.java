package com.project.ohgym.controller;

import com.project.ohgym.service.MapService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Slf4j
public class MapController {

    @Autowired
    private MapService mServ;

    private ModelAndView mv;

    @GetMapping("kakao")
    public ModelAndView kakao(){
        log.info("kakao()");
        mv = mServ.getMapList();
        return mv;
    }

    @GetMapping("search")
    public ModelAndView search(String gname){
        log.info("search()");
        mv = mServ.getSearchList(gname);
        return mv;
    }

}
