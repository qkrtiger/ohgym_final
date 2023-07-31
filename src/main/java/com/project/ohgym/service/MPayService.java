package com.project.ohgym.service;

import com.project.ohgym.dao.GymGoodsDao;
import com.project.ohgym.dao.MPayDao;
import com.project.ohgym.dao.TMemberDao;
import com.project.ohgym.dao.TrainDao;
import com.project.ohgym.dto.GymDto;
import com.project.ohgym.dto.MPayDto;
import com.project.ohgym.dto.TrainDto;
import com.project.ohgym.dto.TrainGoodsDto;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Service
@Slf4j
public class MPayService {
    @Autowired
    private MPayDao mPDao;

    @Autowired
    private TMemberDao tMDao;

    private ModelAndView mv;

    @Autowired
    private GymGoodsDao GGDao;


    public String insertMPay(MPayDto mpay, HttpSession session, RedirectAttributes rttr) {
        log.info("insertMPay()");
        String view = null;
        String msg = null;

        try {
            mPDao.insertMPay(mpay);

            view = "payment?mpaynum=" + mpay.getMpaynum();
            log.info(view);
        } catch (Exception e){
            e.printStackTrace();
        }
        return view;

    }

    public String insertTMPay(MPayDto mpay, HttpSession session, RedirectAttributes rttr) {
        log.info("insertTMPay()");
        String view = null;
        String msg = null;

        try {
            mPDao.insertTMPay(mpay);

            view = "payment?mpaynum=" + mpay.getMpaynum();
            log.info(view);
        } catch (Exception e){
            e.printStackTrace();
        }
        return view;

    }

    public ModelAndView paymentContents(String mpaynum, HttpSession session) {
        log.info("paymentContents()");
        mv = new ModelAndView();

        //주문정보 가져오기
        MPayDto mpay = mPDao.selectPayment(mpaynum);

        mv.addObject("mpay", mpay);

        int gymnum = mpay.getGymnum();
        int tgoodsint = mpay.getTgoodsint();

        //헬스장 정보 가져오기
        GymDto gym = GGDao.selectGym(gymnum);
        //트레이너 정보 가져오기
        TrainDto trainer = tMDao.selectTrain(tgoodsint);


        mv.addObject("gym", gym);
        mv.addObject("trainer", trainer);

        mv.setViewName("payment");

        return mv;
    }
}
