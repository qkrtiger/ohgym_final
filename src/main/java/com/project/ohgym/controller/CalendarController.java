package com.project.ohgym.controller;

import com.project.ohgym.dto.CalendarDto;
import com.project.ohgym.service.CalendarService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Slf4j
public class CalendarController {

    private ModelAndView mv;

    @Autowired
    private CalendarService cServ;

    @GetMapping("calendar")
    public ModelAndView calendar(HttpSession session){
        log.info("calendar()");
        mv = cServ.cShowList(session);
        return mv;
    }

    @PostMapping("insertCal")
    @ResponseBody
    public String insertCal(CalendarDto cal){
        log.info("insertCal");
        String res = cServ.insertCal(cal);
        return res;
    }

    @GetMapping("calDelete")
    public String calDelete(Integer calendarnum, HttpSession session, RedirectAttributes rttr){
        log.info("calDelete()");
        String view = cServ.calDelete(calendarnum, session, rttr);
        return view;
    }
}
