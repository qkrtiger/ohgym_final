package com.project.ohgym.service;

import com.project.ohgym.dao.GymDao;
import com.project.ohgym.dao.TrainDao;
import com.project.ohgym.dto.*;
import com.project.ohgym.util.DeduplicationUtils;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class GymService {
    @Autowired
    private GymDao gDao;

    @Autowired
    private TrainDao tDao;

    private ModelAndView mv;

     //헬스장 목록 출력
    public ModelAndView hGymMList(HttpSession session) {
        log.info("hGymMList()");
        mv = new ModelAndView();
        MemberDto member = (MemberDto) session.getAttribute("member");
        int mn = member.getMembernum();
        List<GymDto> li = gDao.hGymList(mn);
        List<GymDto> gList = DeduplicationUtils.deduplication(li, GymDto::getGname);
        mv.addObject("gList", gList);
        mv.setViewName("gymList");
        return mv;
    }

    public ModelAndView hGymList(){
        log.info("hGymList");
        mv = new ModelAndView();
        List<GymDto> gList = gDao.nonMbGymList();
        mv.addObject("gList", gList);
        mv.setViewName("gymList");
        return mv;
    }

    // 셀렉트 박스 지역 목록 출력
    public List<GymDto> hGymPlaceList(String glocation) {
        log.info("hGymPlaceList()");
        List<GymDto> li = gDao.hGymPlaceList(glocation);
        List<GymDto> addrList = DeduplicationUtils.deduplication(li, GymDto::getGname);
        return addrList;
    }

    // 헬스장 도시명 검색
    public List<GymDto> hSeachGymOutput(String gcity) {
        log.info("hSeachGymOutput()");
        List<GymDto> li = gDao.hSeachGymOutput(gcity);
        List<GymDto> cityList = DeduplicationUtils.deduplication(li, GymDto::getGname);
        return cityList;
    }

    // 필터 검색
    public ModelAndView hGymFilterList(String ggoodsname, String mgender, List<String> check) {
        log.info("hGymFilterList()");
        mv = new ModelAndView();
        Map<String, String> fMap = new HashMap<>();
        fMap.put("ggoodsname", ggoodsname);
        fMap.put("mgender", mgender);

        String andQuery = "";

        for (String i : check) {
            switch (i) {
                case "1":
                    andQuery += " AND gc_cloths = 1";
                    break;
                case "2":
                    andQuery = " AND gc_towel = 1";
                    break;
                case "3":
                    andQuery += " AND gc_wifi = 1";
                    break;
                case "4":
                    andQuery += " AND gc_parking = 1";
                    break;
                case "5":
                    andQuery += " AND gc_inbody = 1";
                    break;
                case "6":
                    andQuery += " AND gc_sauna = 1";
                    break;
                case "7":
                    andQuery += " AND gm_latpull = 1";
                    break;
                case "8":
                    andQuery += " AND gm_shoulderpress = 1";
                    break;
                case "9":
                    andQuery += " AND gm_citydraw = 1";
                    break;
                case "10":
                    andQuery += " AND gm_legpress = 1";
                    break;
                case "11":
                    andQuery += " AND gm_smithmachine = 1";
                    break;
                case "12":
                    andQuery += " AND gm_lyinglegcurl = 1";
                    break;
                case "13":
                    andQuery += " AND gm_chestfly = 1";
                    break;
                case "14":
                    andQuery += " AND gm_hyperextension = 1";
                    break;
            }
        }

        fMap.put("andQuery", andQuery);

        List<FilterViewDto> aList = gDao.hGymFilteraList(fMap);
        List<FilterViewDto> fList = DeduplicationUtils.deduplication(aList, FilterViewDto::getGname);

        mv.addObject("gList", fList);
        mv.setViewName("gymSetList");
        return mv;
    }

    // 헬스장 상세페이지
    public ModelAndView getGymPage(Integer gymnum, Integer membernum) {
        log.info("getGymPage()");
        mv = new ModelAndView();
        GymDetailDto gym = gDao.getGymPage(gymnum);
        GymDetailDto gymConnv = gDao.getGymConven(gymnum);
        GymDetailDto gymMchin = gDao.getGymMechin(gymnum);
        List<GymDetailDto> gGList = gDao.getGoods(gymnum);
        List<GymDetailDto> timg = gDao.getTimg(gymnum);
        String tm = String.valueOf(tDao.getGoods(gymnum));
        int gymMark = gDao.getGymMark(gymnum, membernum);
        int relen = gDao.getlen(gymnum);
        float avg = gDao.getavg(gymnum);
        mv.addObject("gym", gym);
        mv.addObject("gymC", gymConnv);
        mv.addObject("gymM", gymMchin);
        mv.addObject("gGList", gGList);
        mv.addObject("tList", timg);
        mv.addObject("tm", tm);
        mv.addObject("gymMark", gymMark);
        mv.addObject("relen", relen);
        mv.addObject("avg", avg);
        return mv;
    }

    // 비회원 헬스장 상세페이지
    public ModelAndView getNotGymPage(Integer gymnum) {
        log.info("getGymPage()");
        mv = new ModelAndView();
        GymDetailDto gym = gDao.getGymPage(gymnum);
        GymDetailDto gymConnv = gDao.getGymConven(gymnum);
        GymDetailDto gymMchin = gDao.getGymMechin(gymnum);
        List<GymDetailDto> gGList = gDao.getGoods(gymnum);
        List<GymDetailDto> timg = gDao.getTimg(gymnum);
        //int gymMark = gDao.getGymMark(gymnum, membernum);
        int relen = gDao.getlen(gymnum);
        float avg = gDao.getavg(gymnum);
        mv.addObject("gym", gym);
        mv.addObject("gymC", gymConnv);
        mv.addObject("gymM", gymMchin);
        mv.addObject("gGList", gGList);
        mv.addObject("tList", timg);
        //mv.addObject("gymMark", gymMark);
        mv.addObject("relen", relen);
        mv.addObject("avg", avg);
        return mv;
    }

    // 찜 기능
    public String inputMark(Integer membernum, Integer gymnum) {
        log.info("inputMark()");
        String res = null;
        try {
            gDao.insertMark(membernum, gymnum);
            res = "ok";
        } catch (Exception e) {
            e.printStackTrace();
            res = "fail";
        }
        return res;
    }

    // 찜 기능
    public String delMark(Integer membernum, Integer gymnum) {
        log.info("delMark()");
        String res = null;
        try {
            gDao.deleteMark(membernum, gymnum);
            res = "ok";
        } catch (Exception e) {
            e.printStackTrace();
            res = "fail";
        }
        return res;
    }

    public ModelAndView hSearch(String gname) {
        log.info("hSearch()");
        mv = new ModelAndView();
        List<GymDto> li = gDao.hSearch(gname);
        List<GymDto> gnameList = DeduplicationUtils.deduplication(li, GymDto::getGname);
        mv.addObject("gList", gnameList);
        mv.setViewName("gymList");
        return mv;
    }
}
