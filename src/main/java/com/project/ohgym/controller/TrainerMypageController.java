package com.project.ohgym.controller;

import com.project.ohgym.dto.MemberDto;
import com.project.ohgym.dto.TrainDto;
import com.project.ohgym.dto.TrainGoodsDto;
import com.project.ohgym.dto.TrainerDto;
import com.project.ohgym.service.TrainerMypageService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@Slf4j
public class TrainerMypageController {
    @Autowired
    private TrainerMypageService tServ;

    private ModelAndView mv;

    @GetMapping("trainerMypage")
    public ModelAndView tInfo(Integer membernum, HttpSession session) {
        log.info("tInfo()");

        mv = tServ.gettInfo(membernum, session);

        return mv;
    }

    //트레이너 결제내역 가져오기
    @GetMapping("tMypayList")
    public ModelAndView tMPay(Integer tgoodsint, Integer membernum, HttpSession session) {
        log.info("tMypayList()");

        mv = tServ.tMPay(tgoodsint, membernum, session);

        return mv;
    }

    //트레이너 상품관리 페이지
    @GetMapping("trainerGoods")
    public ModelAndView trainerGoods(Integer membernum, HttpSession session) {
        log.info("trainerGoods()");

        mv = tServ.trainerGoods(membernum, session);

        return mv;
    }

    //트레이너 상품 등록 페이지로 이동
    @GetMapping("tGoodsForm")
    public ModelAndView tGoodsForm(HttpSession session) {
        log.info("tGoodsForm()");

        mv = new ModelAndView();

        MemberDto member = (MemberDto) session.getAttribute("member");

        mv.addObject("member", member);

        mv.setViewName("tGoodsForm");

        return mv;
    }

    @PostMapping("tMGoodsSave")
    public String tMGoodsSave(TrainGoodsDto traingoods, HttpSession session, RedirectAttributes rttr) {
        log.info("tMGoodsSave()");

        String view = tServ.tMGoodsSave(traingoods, session, rttr);

        return view;
    }


    @PostMapping("delGoods")
    @ResponseBody
    public String tMGoodsDelete(@RequestParam(value = "selected[]") List<String> selected,
                                HttpSession session) {
        log.info("delGoods");
        int membernum = ((MemberDto) session.getAttribute("member")).getMembernum();

        //db 삭제
        for (String tgoodsintStr : selected) {
            int tgoodsint = Integer.parseInt(tgoodsintStr);
            // tgoodsint를 이용하여 해당 데이터 삭제
            tServ.tMGoodsDelete(tgoodsint);

        }
        return "" + membernum;
    }


    @PostMapping("tMGoodsOnOff")
    @ResponseBody
    public String tMGoodsOnOff(@RequestBody TrainGoodsDto traingoods) {
        log.info("tMGoodsOnOff()");

        //int membernum = ((MemberDto) session.getAttribute("member")).getMembernum();

        tServ.tMGoodsOnOff(traingoods);

        return "ok";
    }

    @GetMapping("tAdditionInfo")
    public ModelAndView AddInfo(Integer membernum, HttpSession session){
        log.info("AddInfo()");

        mv = tServ.getAddInfo(membernum, session);

        return mv;
    }

    @GetMapping("AddInfoMo")
    public ModelAndView AddInfoMo(Integer membernum, HttpSession session){
        log.info("AddInfoMo()");

        mv = tServ.getAddInfoMo(membernum, session);

        return mv;
    }

    @PostMapping("tAddInfoMo")
    public String tAddInfoMo(List<MultipartFile> files, TrainerDto trainer, HttpSession session, RedirectAttributes rttr){
        log.info("tAddInfoMo()");

        String view = tServ.getAddInfoMo(files, trainer, session, rttr);

        return view;
    }


}
