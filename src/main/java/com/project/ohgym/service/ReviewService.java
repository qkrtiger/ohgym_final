package com.project.ohgym.service;

import com.project.ohgym.dao.MPayDao;
import com.project.ohgym.dao.ReviewDao;
import com.project.ohgym.dto.MPayDto;
import com.project.ohgym.dto.ReviewDto;
import com.project.ohgym.dto.SearchDto;
import com.project.ohgym.util.PagingUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Service
@Slf4j
public class ReviewService {
    @Autowired
    private ReviewDao rDao;

    @Autowired
    private MPayDao mPDao;

    private ModelAndView mv;

    // 후기 입력
    public String rwWrite(ReviewDto review, RedirectAttributes rttr) {
        log.info("rwWrite()");
        String view = null;
        String msg = null;
        try {
            rDao.insertreview(review);
            view = "redirect:myPayList?membernum=" + review.getMembernum();
            msg = "후기 작성 성공";
        }catch (Exception e){
            e.printStackTrace();
            view = "redirect:myPayList?membernum=" + review.getMembernum();
            msg = "후기 작성 실패";
        }
        rttr.addFlashAttribute("msg", msg);
        return view;
    }

    // 후기 목록
    private int listCnt = 5;
    public ModelAndView rvList(SearchDto search) {
        log.info("rvList()");
        mv = new ModelAndView();

        if (search.getListCnt() == 0) {
            search.setListCnt(listCnt);
        }

        int num = search.getPageNum();
        if(num == 0) num = 1;

        search.setPageNum((num - 1) * search.getListCnt());

        List<ReviewDto> reList = rDao.rvList(search);
        for (ReviewDto re : reList) {
            int reviewStar = re.getReviewstar();
            re.setRatingOptions(getRatingOption(reviewStar));
        }

        mv.addObject("reList", reList);

        search.setPageNum(num);
        String pageHtml = getPaging(search);
        mv.addObject("paging", pageHtml);
        mv.setViewName("gymReviewList");


        return mv;
    }

    // 별 평점
    private String getRatingOption(int reviewStar) {
        switch (reviewStar) {
            case 0:
                return "☆☆☆☆☆";
            case 1:
                return "★☆☆☆☆";
            case 2:
                return "★★☆☆☆";
            case 3:
                return "★★★☆☆";
            case 4:
                return "★★★★☆";
            case 5:
                return "★★★★★";
            default:
                return "";
        }
    }

    // 페이징처리
    private String getPaging(SearchDto search) {
        log.info("getPaging()");
        String pageHtml = null;

        int maxNum = rDao.selectReviewCnt(search);
        int pageCnt = 5;
        String listName = "gymReviewList?gymnum=" + search.getGymnum() + "&";

        PagingUtil paging = new PagingUtil(maxNum, search.getPageNum(), search.getListCnt(), pageCnt, listName);
        pageHtml = paging.makePaging();
        return pageHtml;
    }

    public ModelAndView gymReview(MPayDto mpay) {
        log.info("gymReview");
        mv = new ModelAndView();
        String mpaynum = mpay.getMpaynum();
        MPayDto mPay = mPDao.selectPayment(mpaynum);
        mv.addObject("mpay", mPay);
        mv.setViewName("gymReview");
        return mv;
    }
}
