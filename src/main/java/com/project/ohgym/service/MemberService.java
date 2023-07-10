package com.project.ohgym.service;

import com.project.ohgym.dao.GymDao;
import com.project.ohgym.dao.MemberDao;
import com.project.ohgym.dto.GmListDto;
import com.project.ohgym.dto.GymDto;
import com.project.ohgym.dto.MemberDto;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.util.List;


@Service
@Slf4j
public class MemberService {
    @Autowired
    private MemberDao mDao;

    @Autowired
    private GymDao gDao;

    private ModelAndView mv;


    public ModelAndView getmInfo(Integer membernum, HttpSession session) {
        log.info("getmInfo()");
        mv = new ModelAndView();

        if(membernum == null){
            membernum = 1;
        }

        MemberDto member = mDao.selectMember(membernum);

        mv.addObject("member", member);

        mv.setViewName("memberMypage");

        return mv;
    }

    public ModelAndView mMarkGym(Integer membernum) {
        log.info("mMarkGym()");
        if(membernum == null){
            membernum = 1;
        }
        mv = new ModelAndView();

        List<GmListDto> gList = mDao.selectMarkedGyms(membernum);

        mv.addObject("gList", gList);
        mv.setViewName("wishList");

        return mv;
    }

    public String insertImage(@RequestPart List<MultipartFile> files, MemberDto member, HttpSession session, RedirectAttributes rttr) {
        log.info("insertImage()");
        String view = null;
        String msg = null;
        //업로드하는 파일의 이름을 먼저 꺼낸다.
        String upFile = files.get(0).getOriginalFilename();

        try{
            //파일 업로드 처리
            if (!upFile.equals("")){
                fileUpload(files,session,member);
            }
            view = "redirect:image";
            msg = "등록 성공";
        }catch (Exception e){
            e.printStackTrace();
            view = "redirect:image";
            msg = "등록 실패";
        }
        rttr.addFlashAttribute("msg", msg);
        return view;
    }

    private void fileUpload(List<MultipartFile> files, HttpSession session, MemberDto member) throws Exception {
        log.info("fileUpload()");
        //파일 저장 위치
        String realPath = session.getServletContext().getRealPath("/");
        realPath += "upload/";//업로드용 폴더 : upload
        File folber = new File(realPath);
        if (folber.isDirectory() == false) {
            folber.mkdir();//폴더 생성.
        }

        //파일 저장 처리(목록이므로 반복 처리)
        for (MultipartFile mf : files) {
            //파일명(원래 이름) 추출
            String orname = mf.getOriginalFilename();
            if (orname.equals("")) {
                return;
            }

            //파일 정보 저장
            MemberDto image = new MemberDto();
            image.setMembernum(member.getMembernum());//회원번호
            image.setMoriname(orname);//원래 파일 이름
            String sysname = System.currentTimeMillis() + orname.substring(orname.lastIndexOf("."));
            image.setMsysname(sysname);

            //파일 저장(to upload folder)
            File file = new File(realPath + sysname);
            mf.transferTo(file);

            //파일 정보 저장(DB)
            mDao.mImage(image);
        }
    }

    public ModelAndView getProfile(Integer membernum) {
        log.info("getProfile()");
        mv = new ModelAndView();

        if(membernum == null){
            membernum = 1;
        }

        MemberDto member = mDao.selectMember(membernum);
        mv.addObject("member", member);
        mv.setViewName("image");

        return mv;
    }

    // 헬스장 고유번호 확인
    public String numCheck(int gnum) {
        log.info("numCheck()");
        String res= null;
        int cnt = gDao.numCheck(gnum);
        if(cnt == 1){
            res = "ok";
        } else {
            res = "fail";
        }
        return res;
    }
}


