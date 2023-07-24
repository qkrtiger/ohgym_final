package com.project.ohgym.controller;

import com.project.ohgym.service.dGymGoodsService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Slf4j
public class dGymGoodsController {

    @Autowired
    private dGymGoodsService dGGServ;

    private ModelAndView mv;


    @GetMapping("gymPay")
    public ModelAndView gymPayContents(Integer gymnum, HttpSession session){
        log.info("gymPay()");

        mv = dGGServ.getGym(gymnum, session);

        return mv;
    }

    @GetMapping("dailyPay")
    public ModelAndView dailyPayContents(Integer gymnum, HttpSession session){
        log.info("dailyPay()");

        mv = dGGServ.getdailyPay(gymnum, session);

        return mv;
    }

    @GetMapping("trainerPage")
    public ModelAndView dGetTrainDetail(Integer membernum, HttpSession session){
        log.info("dGetTrainDetail()");

        mv = dGGServ.dGetTrainDetail(membernum, session);

        return mv;
    }

    @GetMapping("trainerPay")
    public ModelAndView trainerPay(Integer tgoodsint, HttpSession session){
        log.info("trainerPay()");

        mv = dGGServ.trainerPay(tgoodsint, session);

        return mv;
    }
}
