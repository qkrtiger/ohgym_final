package com.project.ohgym.service;

import com.project.ohgym.dao.MapDao;
import com.project.ohgym.dto.GymDto;
import com.project.ohgym.dto.MaptrainDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Service
@Slf4j
public class MapService {

    @Autowired
    private MapDao mapDao;


    private ModelAndView mv;

    public ModelAndView getMapList() {

        log.info("getMapList()");
        mv = new ModelAndView();

        List<GymDto> gymList = mapDao.selectGymList();
        mv.addObject("gymList", gymList);

        List<MaptrainDto> mList = mapDao.selectTrainList();
        mv.addObject("mList", mList);

        mv.addObject("slList", null);

        mv.setViewName("kakao");

        return mv;
    }

    public ModelAndView getSearchList(String gname) {
        log.info("getSearchList()");
        mv = new ModelAndView();

        List<GymDto> sgList = mapDao.selectSearchNameList(gname);
        mv.addObject("sgList", sgList);

        List<GymDto> gymList = mapDao.selectGymList();
        mv.addObject("gymList", gymList);

        List<MaptrainDto> mList = mapDao.selectTrainList();
        mv.addObject("mList", mList);

        mv.setViewName("kakao");

        return mv;
    }

}
