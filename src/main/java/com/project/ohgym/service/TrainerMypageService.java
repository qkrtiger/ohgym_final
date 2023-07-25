package com.project.ohgym.service;


import com.project.ohgym.dao.TrainDao;
import com.project.ohgym.dto.MPayDto;
import com.project.ohgym.dto.TrainGoodsDto;
import com.project.ohgym.dto.TrainDto;
import com.project.ohgym.dto.TrainerDto;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.util.List;

@Service
@Slf4j
public class TrainerMypageService {
    @Autowired
    private TrainDao tDao;

    private ModelAndView mv;

    public ModelAndView gettInfo(Integer membernum, HttpSession session) {
        log.info("gettInfo()");
        mv = new ModelAndView();

        TrainerDto trainer = tDao.selectTrainer(membernum);

        mv.addObject("trainer", trainer);

        mv.setViewName("trainerMypage");

        return mv;
    }

    public ModelAndView tMPay(Integer tgoodsint, Integer membernum, HttpSession session) {
        log.info("tMPay()");
        mv = new ModelAndView();

        //결제내역 가져오기
        List<MPayDto> mPList = tDao.tMPay(tgoodsint, membernum);

        //트레이너 회원정보 가져오기
        TrainerDto trainer = tDao.selectTrainer(membernum);

        mv.addObject("mPList", mPList);
        mv.addObject("trainer", trainer);

        mv.setViewName("tMypayList");

        return mv;
    }

    public ModelAndView trainerGoods(Integer membernum, HttpSession session) {
        log.info("trainerGoods()");

        mv = new ModelAndView();

        //트레이너 상품정보 가져오기
        List<TrainGoodsDto> tGList = tDao.GettrainerGoods(membernum);

        mv.addObject("tGList",tGList);

        mv.setViewName("trainerGoods");

        return mv;
    }

    public String tMGoodsSave(TrainGoodsDto traingoods, HttpSession session, RedirectAttributes rttr) {
        log.info("tMGoodsSave()");
        String view = null;
        String msg = null;

        try{
            tDao.inserttMGoods(traingoods);
            session.setAttribute("traingoods", traingoods);


            view = "redirect:trainerGoods?membernum=" + traingoods.getMembernum();
            msg = "상품 등록 성공";

        } catch (Exception e){
            e.printStackTrace();
            view = "redirect:tGoodsForm";
            msg = "상품 등록 실패";
        }
        rttr.addFlashAttribute("msg", msg);
        return view;
    }

    //트레이너 상품 삭제
    public void tMGoodsDelete(int tgoodsint) {
        log.info("tMGoodsDelete()");

        try{
            //상품 삭제
            tDao.tMGoodsDelete(tgoodsint);

        } catch (Exception e){
            e.printStackTrace();
        }

    }

    public void tMGoodsOnOff(TrainGoodsDto traingoods) {
        log.info("tMGoodsOnOff()");

        try{
            //상품 활성화/비활성화 업데이트
            tDao.tMGoodsOnOff(traingoods);
            //업데이트한 내용 다시 출력
            traingoods = tDao.selectTGoods(traingoods.getTgoodsint());
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public ModelAndView getAddInfo(Integer membernum, HttpSession session) {
        log.info("getAddInfo()");
        mv = new ModelAndView();


        TrainerDto trainer = tDao.selectTrainer(membernum);

        List<TrainGoodsDto> tgList = tDao.trainGoods(membernum);

        mv.addObject("trainer", trainer);
        mv.addObject("tgList", tgList);

        mv.setViewName("tAdditionInfo");

        return mv;
    }

    public ModelAndView getAddInfoMo(Integer membernum, HttpSession session) {
        log.info("getAddInfoMo()");
        mv = new ModelAndView();


        TrainerDto trainer = tDao.selectTrainer(membernum);

        mv.addObject("trainer", trainer);

        mv.setViewName("tAddInfoModify");

        return mv;
    }

    public String getAddInfoMo(List<MultipartFile> files, TrainerDto trainer , HttpSession session, RedirectAttributes rttr) {
        log.info("getAddInfoMo()");
        String view = null;
        String msg = null;


        try{
            tFileUpdate(files, session, trainer);
            tDao.updateAddData(trainer);
            view = "redirect:tAdditionInfo?membernum=" + trainer.getMembernum();
            msg = "수정 성공";
        }catch (Exception e){
            e.printStackTrace();
            view = "redirect:tAdditionInfo?membernum=" + trainer.getMembernum();
            msg = "수정 실패";
        }
        rttr.addFlashAttribute("msg", msg);


        return view;
    }

    private void tFileUpdate(List<MultipartFile> files, HttpSession session, TrainerDto trainer) throws Exception {
        log.info("tFileUpdate()");
        //파일 저장 위치
        String realPath = session.getServletContext().getRealPath("/");
        realPath += "upload/trainer/";//업로드용 폴더 : upload
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
            TrainerDto tImage = new TrainerDto();
            tImage.setMembernum(trainer.getMembernum());//회원번호
            tImage.setToriname(oriname);//원래 파일 이름
            String sysname = System.currentTimeMillis() + oriname.substring(oriname.lastIndexOf("."));
            tImage.setTsysname(sysname);

            //파일 저장(to upload folder)
            File file = new File(realPath + sysname);
            mf.transferTo(file);

            //파일 정보 저장(DB)
            trainer.setTsysname(sysname);
            trainer.setToriname(oriname);
        }
    }
}
