package com.project.ohgym.service;

import com.project.ohgym.dao.BoardDao;
import com.project.ohgym.dao.GymDao;
import com.project.ohgym.dao.MemberDao;
import com.project.ohgym.dto.BoardDto;
import com.project.ohgym.dto.GmListDto;
import com.project.ohgym.dto.GymDto;
import com.project.ohgym.util.DeduplicationUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Service
@Slf4j
public class MainService {
    @Autowired
    private GymDao gDao;

    @Autowired
    private BoardDao bDao;

    @Autowired
    private MemberDao mDao;

    private ModelAndView mv;


    // 게시글 & 최근 헬스장
    public ModelAndView bestBoard() {
        log.info("bestBoard()");
        mv = new ModelAndView();

        //베스트 게시글
        List<BoardDto> best = bDao.bestBoard();
        mv.addObject("best", best);

        //최근 헬스장
        List<GymDto> gList = gDao.newGym();
        mv.addObject("gList",gList);

        mv.setViewName("index");
        return mv;
    }

    public ModelAndView gymPlaceList(String glocation) {
        log.info("gymPlaceList()");
        mv = new ModelAndView();
        List<GymDto> li = gDao.GymPlaceList(glocation);
        List<GymDto> addrList = DeduplicationUtils.deduplication(li, GymDto::getGname);
        mv.addObject("gList", addrList);
        mv.setViewName("gymList");
        return mv;
    }

    public ModelAndView searchList(String gname) {
        log.info("searchList()");
        mv = new ModelAndView();
        List<GymDto> li = gDao.searchList(gname);
        List<GymDto> saarchList = DeduplicationUtils.deduplication(li, GymDto::getGname);
        mv.addObject("gList", saarchList);
        mv.setViewName("gymList");
        return mv;
    }

    public ModelAndView oneList(String goods) {
        log.info("oneList()");
        List<GymDto> mList = gDao.getoneList(goods);
        mv.addObject("gList", mList);
        mv.setViewName("gymList");
        return mv;
    }

    public ModelAndView womanList(String wgender) {
        log.info("womanList()");
        List<GymDto> wList = gDao.getWomanList(wgender);
        mv.addObject("gList", wList);
        mv.setViewName("gymList");
        return mv;
    }

    public ModelAndView manList(String mgender) {
        log.info("manList()");
        List<GymDto> mList = gDao.getManList(mgender);
        mv.addObject("gList", mList);
        mv.setViewName("gymList");
        return mv;
    }

    public ModelAndView mMarkGym(Integer membernum) {
        log.info("mMarkGym()");
        List<GmListDto> gList = mDao.selectMarkedGyms(membernum);
        mv.addObject("gList", gList);
        return mv;
    }
}
