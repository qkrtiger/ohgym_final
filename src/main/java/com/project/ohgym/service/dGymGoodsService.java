package com.project.ohgym.service;

import com.project.ohgym.dao.GymGoodsDao;
import com.project.ohgym.dao.TMemberDao;
import com.project.ohgym.dto.*;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Service
@Slf4j
public class dGymGoodsService {
    @Autowired
    private GymGoodsDao GGDao;

    @Autowired
    private TMemberDao tMDao;

    private ModelAndView mv;

    //헬스장 상품 상세보기
    public ModelAndView getGym(Integer gymnum, HttpSession session){
        log.info("getGym()");
        mv = new ModelAndView();

        MemberDto member = (MemberDto) session.getAttribute("member");

        //헬스장 정보 가져오기
        GymDto gym = GGDao.selectGym(gymnum);

        //헬스 상품 정보 가져오기
        List<GymGoodsDto> gGList = GGDao.selectGymGoods(gymnum);
        //GymGoodsDto gymgoods = gGDao.selectGymGoods(gymnum);

        //가져온 데이터 mv에 담기
        mv.addObject("member", member); //회원 정보
        mv.addObject("gym", gym); //헬스장 정보
        mv.addObject("gGList", gGList); //헬스장 상품 정보


        //mv에 view 지정하기
        mv.setViewName("gymPay");

        return mv;
    }

    //헬스장 일일권 상세보기
    public ModelAndView getdailyPay(Integer gymnum, HttpSession session) {
        log.info("getdailyPay()");
        mv = new ModelAndView();

        //회원 정보 가져오기
        MemberDto member = (MemberDto) session.getAttribute("member");

        //헬스장 정보 가져오기
        GymDto gym = GGDao.selectGym(gymnum);

        //헬스 상품 정보 가져오기
        GymGoodsDto gymgoods = GGDao.selectDailyGymGoods(gymnum);

        //가져온 데이터 mv에 담기
        mv.addObject("member", member); //회원 정보
        mv.addObject("gym", gym); //헬스장 정보
        mv.addObject("gymgoods", gymgoods); //헬스장 상품 정보

        //mv에 view 지정하기
        mv.setViewName("dailyPay");

        return mv;
    }

    //트레이너 상세 페이지
    public ModelAndView dGetTrainDetail(Integer membernum, HttpSession session) {
        log.info("dGetTrainDetail()");
        mv = new ModelAndView();

        //트레이너 정보 가져오기
        TrainDto train = tMDao.selectTrainDetail(membernum);

        //헬스장 정보 가져오기
        GymDto gym = tMDao.selectGymDetail(membernum);

        //트레이너 상품 정보 가져오기
        List<TrainGoodsDto> tGList = tMDao.selectTrainGoods(membernum);

        //가져온 데이터 mv에 담기
        mv.addObject("train", train); //트레이너 정보
        mv.addObject("gym", gym); //헬스장 정보
        mv.addObject("tGList", tGList); //PT 상품 정보

        //mv에 view 지정하기
        mv.setViewName("trainerPage");

        return mv;
    }

    //트레이너 PT 상품 결제 페이지
    public ModelAndView trainerPay(Integer tgoodsint, HttpSession session) {
        log.info("trainerPay()");

        mv = new ModelAndView();

        //회원 정보
        MemberDto member = (MemberDto) session.getAttribute("member");

        //헬스장 정보
        GymDto gym = tMDao.selectGymName(tgoodsint);

        //상품 정보(트레이너)
        TrainGoodsDto traingoods = tMDao.selectTrainGoodsDetail(tgoodsint);

        int membernum = traingoods.getMembernum();
        //트레이너 정보 가져오기
        TrainDto train = tMDao.selectTrainDetail(membernum);


        //mv에 담기
        mv.addObject("train", train); //트레이너 정보
        mv.addObject("member", member);
        mv.addObject("gym", gym);
        mv.addObject("traingoods", traingoods);

        //mv에 view 지정하기
        mv.setViewName("trainerPay");

        return mv;
    }
}
