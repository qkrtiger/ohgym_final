package com.project.ohgym.service;

import com.project.ohgym.dao.GymMypageDao;
import com.project.ohgym.dao.MemberMypageDao;
import com.project.ohgym.dto.GmListDto;
import com.project.ohgym.dto.MPayDto;
import com.project.ohgym.dto.MemberDto;
import com.project.ohgym.dto.SearchDto;
import com.project.ohgym.util.PagingUtil;
import jakarta.servlet.http.HttpSession;
import jakarta.websocket.Session;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@Slf4j
public class MemberMypageService {
    @Autowired
    private MemberMypageDao mDao;

    @Autowired
    private GymMypageDao gDao;

    private ModelAndView mv;

    private int listCnt = 5;



    public ModelAndView getmInfo(Integer membernum, HttpSession session) {
        log.info("getmInfo()");
        mv = new ModelAndView();

        MemberDto member = mDao.selectMember(membernum);

        mv.addObject("member", member);

        mv.setViewName("memberMypage");

        return mv;
    }

    public ModelAndView mMarkGym(Integer membernum) {
        log.info("mMarkGym()");

        mv = new ModelAndView();

        List<GmListDto> gList = mDao.selectMarkedGyms(membernum);
        MemberDto member = mDao.selectMember(membernum);

        mv.addObject("gList", gList);
        mv.addObject("member", member);
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
            view = "redirect:image?membernum=" + member.getMembernum();
            msg = "등록 성공";
        }catch (Exception e){
            e.printStackTrace();
            view = "redirect:image?membernum=" + member.getMembernum();
            msg = "등록 실패";
        }
        rttr.addFlashAttribute("msg", msg);
        return view;
    }

    private void fileUpload(List<MultipartFile> files, HttpSession session, MemberDto member) throws Exception {
        log.info("fileUpload()");
        //파일 저장 위치
        String realPath = session.getServletContext().getRealPath("/");
        realPath += "upload/member/";//업로드용 폴더 : upload
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


        MemberDto member = mDao.selectMember(membernum);
        mv.addObject("member", member);
        //mv.setViewName("image");

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


    public String tChange(Integer gymnum, MemberDto member, HttpSession session, RedirectAttributes rttr) {
        log.info("tChange()");
        String view = null;
        String msg = null;


        member.setGymnum(gymnum);
        member.setMembernum(member.getMembernum());


        try {
            mDao.mtChange(member);
            view = "redirect:trainerMypage?membernum=" + member.getMembernum();
            msg = "트레이너 전환 성공";
        } catch (Exception e) {
            e.printStackTrace();
            view = "redirect:memberMypage?membernum=" + member.getMembernum();
            msg = "트레이너 전환 실패";
        }
        rttr.addFlashAttribute("msg", msg);

        return view;
    }

    public ModelAndView getMemberInfo(Integer membernum, HttpSession session) {
        log.info("getProfile()");
        mv = new ModelAndView();


        MemberDto member = mDao.selectMember(membernum);
        mv.addObject("member", member);
        mv.setViewName("memberInfo");

        return mv;
    }

    public String updateMember( MemberDto member, HttpSession session, RedirectAttributes rttr) {
        log.info("updateMember()");
        String view = null;
        String msg = null;

        try{
            mDao.updateData(member);
            view = "redirect:memberInfo?membernum=" + member.getMembernum();
            msg = "수정 성공";
        }catch (Exception e){
            e.printStackTrace();
            view = "redirect:memberInfo?membernum=" + member.getMembernum();
            msg = "수정 실패";
        }
        rttr.addFlashAttribute("msg", msg);

        return view;
    }

    //결제내역 가져오기
    public ModelAndView mPay(SearchDto search, HttpSession session) {
        log.info("GetMypayList()");
        mv = new ModelAndView();

        int num = search.getPageNum();
        // limit 0, 5 - 1페이지
        // limit 5, 5 - 2페이지
        // limit 10, 5 - 3페이지
        // (pageNum - 1) * lcnt

        //출력할 게시물 수가 설정되지 않으면 기본값(5)로 설정
        if (search.getListCnt() == 0) {
            search.setListCnt(listCnt);
        }
        if(num == 0) num = 1;
        search.setPageNum((num - 1) * search.getListCnt()); // 보여질 게시글 목록의 수

        //결제내역 가져오기
        List<MPayDto> mPList = mDao.GetMypayList(search);

        //회원정보 가져오기
        MemberDto member = mDao.selectMemberSearch(search);

        mv.addObject("mPList", mPList);
        mv.addObject("member", member);

        //페이징 처리
        search.setPageNum(num);
        String pageHtml = null;
        String payKind = null;
        if(search.getMpayType() == null || search.getMpayType().equals("")){
            pageHtml = getPaging(search);
        }else {
            pageHtml = getMpayPaging(search);
            payKind = search.getMpayType();
        }

        mv.addObject("paging", pageHtml);
        mv.addObject("payType", payKind);

        //세션에 필요 정보 저장(pageNum)
        session.setAttribute("pageNum", num);


        mv.setViewName("myPayList");
        return mv;
    }

    //페이징 처리 메소드
    private String getPaging(SearchDto search) {
        log.info("getPaging()");
        String pageHtml = null;

        //결제내역 개수 구하기
        int maxNum = mDao.selectMPayCnt(search);
        //페이지에 보여질 번호 개수
        int pageCnt = 5;
        String listName = "myPayList?membernum=" +search.getMembernum() +"&";

        PagingUtil paging = new PagingUtil(maxNum, search.getPageNum(), search.getListCnt(), pageCnt, listName);
        pageHtml = paging.makePaging();
        return pageHtml;
    }

    //결제내역 조회(회원권, 일일권, PT)
    public Map<String, Object> selectedMpay(SearchDto search) {
        log.info("selectedMpay()");

        List<MPayDto> mPList = null;
        Map<String, Object> rmap = new HashMap<>();

        int num = search.getPageNum();

        //출력할 게시물 수가 설정되지 않으면 기본값(5)로 설정
        if (search.getListCnt() == 0) {
            search.setListCnt(listCnt);
        }
        if(num == 0) num = 1;
        search.setPageNum((num - 1) * search.getListCnt());

        try{
            mPList = mDao.selectedMpay(search);
            rmap.put("mPList", mPList);

            search.setPageNum(num);
            String pageHtml = getMpayPaging(search);
            rmap.put("paging", pageHtml);

        } catch (Exception e){
            e.printStackTrace();
        }
        return rmap;
    }

    private String getMpayPaging(SearchDto search) {
        log.info("getMpayPaging()");
        String pageHtml = null;

        //결제내역 개수 구하기
        int maxNum = mDao.selectMpayCount(search);
        //페이지에 보여질 번호 개수
        int pageCnt = 5;
        String listName = "myPayList?membernum="
                + search.getMembernum()
                + "&mpayType="
                + search.getMpayType() + "&";

        PagingUtil paging = new PagingUtil(maxNum, search.getPageNum(), search.getListCnt(), pageCnt, listName);
        pageHtml = paging.makePaging();
        return pageHtml;
    }

}


