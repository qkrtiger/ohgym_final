package com.project.ohgym.service;

import com.project.ohgym.dao.GymDao;
import com.project.ohgym.dao.GymMypageDao;
import com.project.ohgym.dto.*;
import com.project.ohgym.util.PagingUtil;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
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
public class GymMypageService {
    @Autowired
    private GymMypageDao gDao;

    private ModelAndView mv;

    private int listCnt = 5;

    public ModelAndView getmInfo(Integer gymnum, HttpSession session) {
        log.info("getmInfo()");

        mv = new ModelAndView();

        //if(gymnum == null){
        //    gymnum = 1;
        //}

        GymDto gym = gDao.selectGym(gymnum);

        mv.addObject("gym", gym);
        mv.setViewName("gymMypage");

        return mv;
    }

    public ModelAndView getProfileGym(Integer gymnum) {
        log.info("getProfile()");
        mv = new ModelAndView();

        //if (gymnum == null) {
        //    gymnum = 1;
        //}

        GymDto gym = gDao.selectGym(gymnum);
        mv.addObject("gym", gym);
        mv.setViewName("popup");
        return mv;
    }


    public String insertImage(@RequestPart List<MultipartFile> files, GymDto gym, HttpSession session, RedirectAttributes rttr) {
        log.info("insertImage()");
        String view = null;
        String msg = null;
        //업로드하는 파일의 이름을 먼저 꺼낸다.
        String upFile = files.get(0).getOriginalFilename();

        GymDto gyms = (GymDto) session.getAttribute("gym");
        int gymnum = gyms.getGymnum();

        try{
            //파일 업로드 처리
            if (!upFile.equals("")){
                fileUpload(files,session,gym);
            }
            view = "redirect:gImage?gymnum=" + gymnum;
            msg = "등록 성공";
        }catch (Exception e){
            e.printStackTrace();
            view = "redirect:gImage?gymnum=" + gymnum;
            msg = "등록 실패";
        }
        rttr.addFlashAttribute("msg", msg);
        return view;
    }

    private void fileDelete(String image, HttpSession session) throws Exception {
        log.info("fileDelete()");
        String realPath = session.getServletContext().getRealPath("/");
        realPath += "upload/gym/" + image;
        File file = new File(realPath);
        if(file.exists()){
            file.delete();
        }
    }

    private void fileUpload(List<MultipartFile> files, HttpSession session, GymDto gym) throws Exception {
        log.info("fileUpload()");
        //파일 저장 위치
        String realPath = session.getServletContext().getRealPath("/");
        realPath += "upload/gym/";//업로드용 폴더 : upload
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
            GymDto image = new GymDto();
            image.setGymnum(gym.getGymnum());//회원번호
            image.setGoriname(orname);//원래 파일 이름
            String sysname = System.currentTimeMillis() + orname.substring(orname.lastIndexOf("."));
            image.setGsysname(sysname);

            //파일 저장(to upload folder)
            File file = new File(realPath + sysname);
            mf.transferTo(file);

            //파일 정보 저장(DB)
            gDao.gImage(image);
        }
    }

    public ModelAndView getgAddInfo(Integer gymnum, HttpSession session) {
        log.info("getgAddInfo()");
        mv = new ModelAndView();

        GymDto gym = gDao.selectGym(gymnum);

        List<GymGoodsDto> ggList = gDao.gymGoods(gymnum);

        GymCmDto gmc = gDao.gmcCheck(gymnum);

        mv.addObject("gym", gym);
        mv.addObject("ggList", ggList);
        mv.addObject("gmc", gmc);

        mv.setViewName("gAdditionInfo");

        return mv;
    }

    public ModelAndView getgAddInfoMo(Integer gymnum) {
        log.info("getgAddInfoMo()");
        mv = new ModelAndView();


        GymDto gym = gDao.selectGym(gymnum);
        GymCmDto gmc = gDao.gmcCheck(gymnum);

        mv.addObject("gym", gym);
        mv.addObject("gmc", gmc);

        mv.setViewName("gAddInfoModify");

        return mv;
    }


    private void gFileUpdate(List<MultipartFile> files, HttpSession session, GymDto gym) throws Exception {
        log.info("gFileUpdate()");
        //파일 저장 위치
        String realPath = session.getServletContext().getRealPath("/");
        realPath += "upload/gym/";//업로드용 폴더 : upload
        File folber = new File(realPath);
        if (folber.isDirectory() == false) {
            folber.mkdir();//폴더 생성.
        }

        //파일 저장 처리(목록이므로 반복 처리)
        for (MultipartFile mf : files) {
            //파일명(원래 이름) 추출
            String oriname = mf.getOriginalFilename();
            if (oriname.equals("")) {
                return;
            }

            //파일 정보 저장
            GymDto gImage = new GymDto();
            gImage.setGymnum(gym.getGymnum());//회원번호
            gImage.setGoriname(oriname);//원래 파일 이름
            String sysname = System.currentTimeMillis() + oriname.substring(oriname.lastIndexOf("."));
            gImage.setGsysname(sysname);

            //파일 저장(to upload folder)
            File file = new File(realPath + sysname);
            mf.transferTo(file);

            //파일 정보 저장(DB)
            gym.setGsysname(sysname);
            gym.setGoriname(oriname);
        }
    }


    public String getAddInfoMo(GymCmDto gcd, GymDto gym,
                               List<MultipartFile> files,
                               HttpSession session,
                               RedirectAttributes rttr) {
        log.info("getAddInfoMo()");
        String view = null;
        String msg = null;

        Integer gymnum = gym.getGymnum();
        GymConvenientDto gConv = new GymConvenientDto();
        GymMachineDto gMach = new GymMachineDto();

        gConv.setGymnum(gymnum);
        gConv.setGc_cloths(gcd.isGc_cloths());
        gConv.setGc_towel(gcd.isGc_towel());
        gConv.setGc_inbody(gcd.isGc_inbody());
        gConv.setGc_sauna(gcd.isGc_sauna());
        gConv.setGc_wifi(gcd.isGc_wifi());
        gConv.setGc_parking(gcd.isGc_parking());

        gMach.setGymnum(gymnum);
        gMach.setGm_shoulderpress(gcd.isGm_shoulderpress());
        gMach.setGm_citydraw(gcd.isGm_citydraw());
        gMach.setGm_legpress(gcd.isGm_legpress());
        gMach.setGm_smithmachine(gcd.isGm_smithmachine());
        gMach.setGm_lyinglegcurl(gcd.isGm_lyinglegcurl());
        gMach.setGm_chestfly(gcd.isGm_chestfly());
        gMach.setGm_hyperextension(gcd.isGm_hyperextension());

        try{
            gDao.updateGymConvenient(gConv);
            gDao.updateGymMachine(gMach);
            gFileUpdate(files, session, gym);
            gDao.updateAddData(gym);
            msg = "수정 성공";
        } catch(Exception e){
            e.printStackTrace();
            msg = "수정 실패";
        }
        view = "redirect:gAdditionInfo?gymnum=" + gym.getGymnum();
        rttr.addFlashAttribute("msg",msg);

        return view;
    }

    public ModelAndView getGymTrainer(Integer gymnum) {
        log.info("getGymTrainer()");
        ModelAndView mv = new ModelAndView();
        List<GymMemberDto> train = gDao.selectTraomer(gymnum);

        //getGmList(gym, session);
        GymDto gym = gDao.selectGym(gymnum);

        mv.addObject("trains", train);
        mv.addObject("gym", gym);
        mv.setViewName("gymTrainer");

        return mv;
    }


    public String deleteGT(Integer membernum, Integer gymnum, RedirectAttributes rttr) {
        log.info("deleteGT()");
        String view = null;
        String msg = null;

        try{
            gDao.deleteGymTrainer(membernum);
            msg = "수정 성공";
        }catch (Exception e){
            e.printStackTrace();
            msg = "수정 실패";
        }

        view = "redirect:gymTrainer?gymnum=" + gymnum;
        rttr.addFlashAttribute("msg", msg);

        return view;
    }


    public ModelAndView GetgMypayList(SearchDto search, HttpSession session) {
        log.info("GetgMypayList()");
        mv = new ModelAndView();

        try{
            int num = search.getPageNum();

            //출력할 게시물 수가 설정되지 않으면 기본값(5)로 설정
            if (search.getListCnt() == 0) {
                search.setListCnt(listCnt);
            }
            if(num == 0) num = 1;
            search.setPageNum((num - 1) * search.getListCnt()); // 보여질 게시글 목록의 수

            //결제내역 가져오기
            List<MPayDto> mPList = gDao.GetgMypayList(search);

            //헬스장 정보 가져오기
            GymDto gym = gDao.selectgPay(search);

            mv.addObject("mPList", mPList);
            mv.addObject("gym", gym);

            //페이징 처리
            search.setPageNum(num);
            String pageHtml = getPaging(search);
            mv.addObject("paging", pageHtml);

            //세션에 필요 정보 저장(pageNum)
            session.setAttribute("pageNum", num);

            mv.setViewName("gMypayList");

        } catch (Exception e){
            e.printStackTrace();
        }
        return mv;
    }

    //페이징 처리 메소드
    private String getPaging(SearchDto search) {
        log.info("getPaging()");
        String pageHtml = null;

        //결제내역 개수 구하기
        int maxNum = gDao.selectgMPayCnt(search);
        //페이지에 보여질 번호 개수
        int pageCnt = 5;
        String listName = "gMypayList?gymnum=" +search.getGymnum() +"&";

        PagingUtil paging = new PagingUtil(maxNum, search.getPageNum(), search.getListCnt(), pageCnt, listName);
        pageHtml = paging.makePaging();
        return pageHtml;
    }

    //결제내역 조회(회원권, 일일권, PT)
    public Map<String, Object> selectedgMpay(SearchDto search) {
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
            mPList = gDao.selectedgMpay(search);

            search.setPageNum(num);
            String pageHtml = getgMpayPaging(search);
            rmap.put("paging", pageHtml);
            rmap.put("mPList", mPList);

        } catch (Exception e){
            e.printStackTrace();
        }
        return rmap;
    }

    private String getgMpayPaging(SearchDto search) {
        log.info("getgMpayPaging()");
        String pageHtml = null;

        //결제내역 개수 구하기
        int maxNum = gDao.selectgMpayCount(search);
        //페이지에 보여질 번호 개수
        int pageCnt = 5;
        String listName = "gMypayList?gymnum="
                + search.getGymnum()
                + "&mpayType="
                + search.getMpayType() + "&";

        PagingUtil paging = new PagingUtil(maxNum, search.getPageNum(), search.getListCnt(), pageCnt, listName);
        pageHtml = paging.makePaging();
        return pageHtml;
    }

    public ModelAndView gymGoods(Integer gymnum, HttpSession session) {
        log.info("gymGoods()");

        //if(gymnum == null){
        //    gymnum = 1;
        //}

        mv = new ModelAndView();

        //헬스장 상품정보 가져오기
        List<GymGoodsDto> gGList = gDao.GetgymGoods(gymnum);

        mv.addObject("gGList",gGList);

        mv.setViewName("gymGoods");

        return mv;
    }

    public String gGoodsSave(GymGoodsDto gymgoods, HttpSession session, RedirectAttributes rttr) {
        log.info("gGoodsSave()");
        String view = null;
        String msg = null;

        try{
            gDao.insertgGoodsSave(gymgoods);
            session.setAttribute("gymgoods", gymgoods);


            view = "redirect:gymGoods?gymnum=" + gymgoods.getGymnum();
            msg = "상품 등록 성공";

        } catch (Exception e){
            e.printStackTrace();
            view = "redirect:gGoodsForm";
            msg = "상품 등록 실패";
        }
        rttr.addFlashAttribute("msg", msg);
        return view;
    }

    //헬스장 상품 삭제
    public void gMGoodsDelete(int ggoodsnum) {
        log.info("gMGoodsDelete()");

        try{
            //상품 삭제
            gDao.gMGoodsDelete(ggoodsnum);

        } catch (Exception e){
            e.printStackTrace();
        }

    }

    //헬스장 상품 활성화/비활성화 관리
    public void gGoodsOnOff(GymGoodsDto gymgoods) {
        log.info("gGoodsOnOff()");

        try{
            //상품 활성화/비활성화 업데이트
            gDao.gGoodsOnOff(gymgoods);
            //업데이트한 내용 다시 출력
            gymgoods = gDao.selectGGoods(gymgoods.getGgoodsnum());
        } catch (Exception e){
            e.printStackTrace();
        }
    }


}
