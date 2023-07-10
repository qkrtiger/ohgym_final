//package com.project.ohgym.controller;
//
//import com.project.ohgym.dao.GymDao;
//import com.project.ohgym.dto.GymDto;
//import com.project.ohgym.dto.MemberDto;
//import com.project.ohgym.service.GymService;
//import jakarta.servlet.http.HttpSession;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestPart;
//import org.springframework.web.multipart.MultipartFile;
//import org.springframework.web.servlet.ModelAndView;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
//import java.util.List;
//
//@Controller
//@Slf4j
//public class GymController {
//    @Autowired
//    private GymService gServ;
//
//    private ModelAndView mv;
//
//
//    @GetMapping("gymMypage")
//    public ModelAndView gInfo(Integer gymnum, HttpSession session){
//        log.info("gInfo()");
//
//        mv = gServ.getmInfo(gymnum, session);
//        return mv;
//    }
//
//    //팝업창(이미지수정)
//    @GetMapping("popup")
//    public ModelAndView popup(Integer gymnum){
//        log.info("popup()");
//
//        mv = gServ.getProfileGym(gymnum);
//        mv.setViewName("popup");
//
//        return mv;
//    }
//
//    //이미지수정
//    @PostMapping("gImage")
//    public String gImage(@RequestPart(required = false) List<MultipartFile> files, GymDto gym, HttpSession session, RedirectAttributes rttr) {
//        log.info("gImage()");
//        String view = gServ.insertImage(files, gym, session, rttr);
//        return view;
//    }
//
//    @GetMapping("gProfile")
//    public ModelAndView gProfile(Integer gymnum){
//        log.info("gProfile()");
//
//        mv = gServ.getProfileGym(gymnum);
//        mv.setViewName("gProfile");
//        return mv;
//    }
//}
