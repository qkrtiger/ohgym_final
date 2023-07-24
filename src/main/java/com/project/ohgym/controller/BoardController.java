package com.project.ohgym.controller;

import com.project.ohgym.dto.BoardDto;
import com.project.ohgym.dto.ReplyDto;
import com.project.ohgym.dto.SearchDto;
import com.project.ohgym.service.BoardService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Slf4j
public class BoardController {
    // boardService 사용(Autowired)
    @Autowired
    private BoardService bServ;

    private ModelAndView mv;


    // 게시판 목록
    @GetMapping("board")
    public ModelAndView board(SearchDto search, HttpSession session){
        log.info("board()");
        mv = bServ.bShowList(search, session);
        return mv;
    }


    // 입력 링크
    @GetMapping("boardWrite")
    public String boardWrite(){
        log.info("boardWrite()");
        return "boardWrite";
    }

    // 글쓰기
    @PostMapping("bWrite")
    public String bWrite(BoardDto board, RedirectAttributes rttr){
        log.info("bWrite()");
        String view = bServ.bWrite(board, rttr);
        return view;
    }

    // 헬스장 글쓰기
    @PostMapping("bGWrite")
    public String bGWrite(BoardDto board, RedirectAttributes rttr){
        log.info("bGWrite()");
        String view = bServ.bGWrite(board, rttr);
        return view;
    }

    // 상세보기
    @GetMapping("boardDetail")
    public ModelAndView bContent(Integer boardnum) {
        log.info("bContent()");
        mv = bServ.bContent(boardnum);
        return mv;
    }

    // 상세보기
    @GetMapping("boardDetailM")
    public ModelAndView bContentN(Integer boardnum) {
        log.info("bContentN()");
        mv = bServ.bContentN(boardnum);
        return mv;
    }

    // 댓글 입력
    @PostMapping("replyInsert")
    @ResponseBody
    public ReplyDto replyInsert(ReplyDto reply){
        log.info("replyInsert()");
        reply = bServ.replyInsert(reply);
        return reply;
    }

    // 댓글 입력
    @PostMapping("replyGymInsert")
    @ResponseBody
    public ReplyDto replyGymInsert(ReplyDto reply){
        log.info("replyInsert()");
        reply = bServ.replyGymInsert(reply);
        return reply;
    }

    // 수정 입력
    @GetMapping("boardUpdate")
    public ModelAndView boardUpdate(Integer boardnum){
        log.info("boardUpdate()");
        mv = bServ.bContent(boardnum);
        mv.setViewName("boardUpdate");
        return mv;
    }

    // 수정
    @PostMapping("bUpdate")
    public String bUpdate(BoardDto board,RedirectAttributes rttr) {
        log.info("bUpdate()");
        String view = bServ.bUpdate(board, rttr);
        return view;
    }

    // 삭제
    @GetMapping("bDelete")
    public String bDelete(Integer boardnum, HttpSession session, RedirectAttributes rttr) {
        log.info("bDelete()");
        String view = bServ.bDelete(boardnum, session, rttr);
        return view;
    }
}
