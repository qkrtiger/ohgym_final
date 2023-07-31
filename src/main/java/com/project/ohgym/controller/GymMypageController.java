package com.project.ohgym.controller;

import com.project.ohgym.dto.*;
import com.project.ohgym.service.GymMypageService;
import com.project.ohgym.service.GymService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

@Controller
@Slf4j
public class GymMypageController {
    @Autowired
    private GymMypageService gServ;

    private ModelAndView mv;


    @GetMapping("gymMypage")
    public ModelAndView gInfo(Integer gymnum, HttpSession session){
        log.info("gInfo()");

        mv = gServ.getmInfo(gymnum, session);
        return mv;
    }

    //팝업창(이미지수정)
    @GetMapping("gImage")
    public ModelAndView gImage(Integer gymnum){
        log.info("gImage()");

        mv = gServ.getProfileGym(gymnum);
        mv.setViewName("gImage");

        return mv;
    }

    //이미지수정
    @PostMapping("gImage")
    public String gImage(@RequestPart(required = false) List<MultipartFile> files, GymDto gym, HttpSession session, RedirectAttributes rttr) {
        log.info("gImage()");
        String view = gServ.insertImage(files, gym, session, rttr);
        return view;
    }

    @GetMapping("gProfile")
    public ModelAndView gProfile(Integer gymnum){
        log.info("gProfile()");

        mv = gServ.getProfileGym(gymnum);
        mv.setViewName("gProfile");
        return mv;
    }

    @GetMapping("gAdditionInfo")
    public ModelAndView gAddInfo(Integer gymnum, HttpSession session){
        log.info("gAddInfo()");

        mv = gServ.getgAddInfo(gymnum, session);

        return mv;
    }

    @GetMapping("gAddInfoMo")
    public ModelAndView gAddInfoMo(Integer gymnum, HttpSession session){
        log.info("gAddInfoMo()");

        mv = gServ.getgAddInfoMo(gymnum);

        return mv;
    }

    @PostMapping("gymAddInfoMo")
    public String getAddInfoMo(GymCmDto gcd, GymDto gym,
                               List<MultipartFile> files,
                               HttpSession session,
                               RedirectAttributes rttr){
        log.info("gymAddInfoMo()");

        String view = gServ.getAddInfoMo(gcd, gym, files, session, rttr);

        return view;
    }

    @GetMapping("gymTrainer")
    public ModelAndView gymTrainer(Integer gymnum){
        log.info("gymTainer()");

        mv = gServ.getGymTrainer(gymnum);

        return mv;
    }



    @GetMapping("delete")
    public String deleteGymTrainer(Integer membernum, Integer gymnum, RedirectAttributes rttr){
        log.info("deleteGymTrainer()");

        String view = gServ.deleteGT(membernum, gymnum, rttr);

        return view;
    }

    //결제 내역 가져오기
    @GetMapping("gMypayList")
    public ModelAndView gMypayList(SearchDto search, HttpSession session){
        log.info("gMypayList()");

        mv = gServ.GetgMypayList(search, session);
        return mv;
    }

    //결제내역 조회(회원권, 일일권, pt)
    @PostMapping("selectedgMpay")
    @ResponseBody
    public Map<String, Object> selectedgMpay(@RequestBody SearchDto search){
        log.info("selectedgMpay");
//        String mpayType = (String)map.get("mpayType");
//        int gymnum = (Integer)map.get("gymnum");

        Map<String, Object> rmap = gServ.selectedgMpay(search);

        return rmap;
    }

    //헬스장 상품관리 페이지
    @GetMapping("gymGoods")
    public ModelAndView gymGoods(Integer gymnum, HttpSession session) {
        log.info("gymGoods()");

        mv = new ModelAndView();

        //로그인 세팅하고 지우기★★★★★★★★
        // Gym 테이블의 데이터 생성
        //GymDto gym = new GymDto();
        //gym.setGymnum(1); // 예시로 gymnum 설정
        // 세션에 Gym 데이터 저장
        //session.setAttribute("gym", gym);
        //★★★★★★★★

        mv = gServ.gymGoods(gymnum, session);

        return mv;
    }

    //헬스장 상품 등록 페이지로 이동
    @GetMapping("gGoodsForm")
    public ModelAndView gGoodsForm(HttpSession session) {
        log.info("gGoodsForm()");

        mv = new ModelAndView();

        //로그인 세팅하고 지우기★★★★★★★★
        // Gym 테이블의 데이터 생성
        //GymDto gym = new GymDto();
        //gym.setGymnum(1); // 예시로 gymnum 설정
        // 세션에 Gym 데이터 저장
        //session.setAttribute("gym", gym);
        //★★★★★★★★

        //로그인 세팅되면 살리기★★★★★★★★
        GymDto gym = (GymDto) session.getAttribute("gym");

        mv.addObject("gym", gym);

        mv.setViewName("gGoodsForm");

        return mv;
    }

    //헬스장 상품등록 처리
    @PostMapping("gMGoodsSave")
    public String gMGoodsSave(GymGoodsDto gymgoods, HttpSession session, RedirectAttributes rttr) {
        log.info("gMGoodsSave()");

        String view = gServ.gGoodsSave(gymgoods, session, rttr);

        return view;
    }

    //헬스장 상품 삭제
    @PostMapping("delGGoods")
    @ResponseBody
    public String gMGoodsDelete(@RequestParam(value = "selected[]") List<String> selected,
                                HttpSession session) {
        log.info("delGGoods()");

        //로그인 세팅하고 지우기★★★★★★★★
        // Gym 테이블의 데이터 생성
        //GymDto gym = new GymDto();
        //gym.setGymnum(1); // 예시로 gymnum 설정
        // 세션에 Gym 데이터 저장
        //session.setAttribute("gym", gym);
        //★★★★★★★★

        int gymnum = ((GymDto) session.getAttribute("gym")).getGymnum();

        //db 삭제
        for (String ggoodsnumStr : selected) {
            int ggoodsnum = Integer.parseInt(ggoodsnumStr);
            // ggoodsnum 이용하여 해당 데이터 삭제
            gServ.gMGoodsDelete(ggoodsnum);

        }
        return "" + gymnum;
    }

    //헬스장 상품 활성화/비활성화 관리
    @PostMapping("gGoodsOnOff")
    @ResponseBody
    public String gGoodsOnOff(@RequestBody GymGoodsDto gymgoods) {
        log.info("gGoodsOnOff()");

        gServ.gGoodsOnOff(gymgoods);

        return "ok";
    }
}
