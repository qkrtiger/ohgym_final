//package com.project.ohgym.service;
//
//import com.project.ohgym.dao.GymDao;
//import com.project.ohgym.dto.GymDto;
//import com.project.ohgym.dto.MemberDto;
//import jakarta.servlet.http.HttpSession;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.web.bind.annotation.RequestPart;
//import org.springframework.web.multipart.MultipartFile;
//import org.springframework.web.servlet.ModelAndView;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
//import java.io.File;
//import java.util.List;
//
//@Service
//@Slf4j
//public class GymService {
//    @Autowired
//    private GymDao gDao;
//
//    private ModelAndView mv;
//
//    public ModelAndView getmInfo(Integer gymnum, HttpSession session) {
//        log.info("getmInfo()");
//
//        ModelAndView mv = new ModelAndView();
//
//        if(gymnum == null){
//            gymnum = 1;
//        }
//
//        GymDto gym = gDao.selectGym(gymnum);
//
//        mv.addObject("gym", gym);
//        mv.setViewName("gymMypage");
//
//        return mv;
//    }
//
//
//    public String insertImage(@RequestPart List<MultipartFile> files, GymDto gym, HttpSession session, RedirectAttributes rttr) {
//        log.info("insertImage()");
//        String view = null;
//        String msg = null;
//        //업로드하는 파일의 이름을 먼저 꺼낸다.
//        String upFile = files.get(0).getOriginalFilename();
//
//        try{
//            //파일 업로드 처리
//            if (!upFile.equals("")){
//                fileUpload(files,session,gym);
//            }
//            view = "redirect:popup";
//            msg = "등록 성공";
//        }catch (Exception e){
//            e.printStackTrace();
//            view = "redirect:popup";
//            msg = "등록 실패";
//        }
//        rttr.addFlashAttribute("msg", msg);
//        return view;
//    }
//
//    private void fileUpload(List<MultipartFile> files, HttpSession session, GymDto gym) throws Exception {
//        log.info("fileUpload()");
//        //파일 저장 위치
//        String realPath = session.getServletContext().getRealPath("/");
//        realPath += "upload/";//업로드용 폴더 : upload
//        File folber = new File(realPath);
//        if (folber.isDirectory() == false) {
//            folber.mkdir();//폴더 생성.
//        }
//
//        //파일 저장 처리(목록이므로 반복 처리)
//        for (MultipartFile mf : files) {
//            //파일명(원래 이름) 추출
//            String orname = mf.getOriginalFilename();
//            if (orname.equals("")) {
//                return;
//            }
//
//            //파일 정보 저장
//            GymDto image = new GymDto();
//            image.setGymnum(gym.getGymnum());//회원번호
//            image.setGoriname(orname);//원래 파일 이름
//            String sysname = System.currentTimeMillis() + orname.substring(orname.lastIndexOf("."));
//            image.setGsysname(sysname);
//
//            //파일 저장(to upload folder)
//            File file = new File(realPath + sysname);
//            mf.transferTo(file);
//
//            //파일 정보 저장(DB)
//            gDao.gImage(image);
//        }
//    }
//
//    public ModelAndView getProfileGym(Integer gymnum) {
//        log.info("getProfile()");
//        mv = new ModelAndView();
//
//        if (gymnum == null) {
//            gymnum = 1;
//        }
//
//        GymDto gym = gDao.selectGym(gymnum);
//        mv.addObject("gym", gym);
//        mv.setViewName("popup");
//        return mv;
//    }
//}
