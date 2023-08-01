package com.project.ohgym.service;

import com.project.ohgym.dao.CalendarDao;
import com.project.ohgym.dto.CalendarDto;
import com.project.ohgym.dto.MemberDto;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Service
@Slf4j
public class CalendarService {
    @Autowired
    private CalendarDao cDao;

    private ModelAndView mv;

    public ModelAndView cShowList(HttpSession session){
        log.info("cShowList()");
        mv = new ModelAndView();

        MemberDto member = (MemberDto) session.getAttribute("member");
        int membernum = member.getMembernum();

        List<CalendarDto> cList = cDao.cShowList(membernum);

        mv.addObject("cList", cList);
        mv.setViewName("calendar");
        return mv;
    };

    public String insertCal(CalendarDto cal){
        log.info("insertCal");
        String res;
        try {
            cDao.calInsert(cal);
            res = "ok";
        } catch (Exception e){
            e.printStackTrace();
            res = "fail";
        }
        return res;
    };

    public String calDelete(Integer calendarnum, HttpSession session, RedirectAttributes rttr) {
        log.info("calDelete()");
        String view = null;
        String msg = null;

        try {
            cDao.deleteCal(calendarnum);
            view = "redirect:calendar";
        } catch (Exception e){
            view = "redirect:calendar";
            msg = "삭제 실패";
        }
        rttr.addFlashAttribute("msg", msg);
        return view;
    }
}
