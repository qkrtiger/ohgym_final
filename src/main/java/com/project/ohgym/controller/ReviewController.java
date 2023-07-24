package com.project.ohgym.controller;

import com.project.ohgym.dto.MPayDto;
import com.project.ohgym.dto.ReviewDto;
import com.project.ohgym.dto.SearchDto;
import com.project.ohgym.service.ReviewService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Slf4j
public class ReviewController {
    @Autowired
    private ReviewService rServ;
    private ModelAndView mv;

    // 후기
    @GetMapping("gymReview")
    public ModelAndView gymReview(MPayDto mpay) {
        log.info("gymReview()");
        mv = rServ.gymReview(mpay);
        return mv;
    }

    // 후기 입력
    @PostMapping("reviewInput")
    public String rwWrite(ReviewDto review, RedirectAttributes rttr) {
        log.info("rwWrite()");
        String view = rServ.rwWrite(review, rttr);
        return view;
    }

    // 후기 출력
    @GetMapping("gymReviewList")
    public ModelAndView rvList(SearchDto search){
        log.info("rvList()");
        mv = rServ.rvList(search);
        return mv;
    }
}
