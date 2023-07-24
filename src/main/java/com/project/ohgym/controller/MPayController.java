package com.project.ohgym.controller;

import com.project.ohgym.dto.MPayDto;
import com.project.ohgym.service.MPayService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Slf4j
public class MPayController {
    @Autowired
    private MPayService mPServ;

    private ModelAndView mv;

    @PostMapping("insertMPay")
    @ResponseBody
    public String insertMPay(@RequestBody MPayDto mpay, HttpSession session, RedirectAttributes rttr){
        log.info("insertMPay()");

        String view = mPServ.insertMPay(mpay, session, rttr);
        return view;
    }

    @PostMapping("insertTMPay")
    @ResponseBody
    public String insertTMPay(@RequestBody MPayDto mpay, HttpSession session, RedirectAttributes rttr){
        log.info("insertMPay()");

        String view = mPServ.insertTMPay(mpay, session, rttr);
        return view;
    }

    @GetMapping("payment")
    public ModelAndView paymentContents(String mpaynum, HttpSession session){
        log.info("paymentContents()");

        mv = mPServ.paymentContents(mpaynum, session);
        return mv;
    }
}
