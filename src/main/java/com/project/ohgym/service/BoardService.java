package com.project.ohgym.service;

import com.project.ohgym.dao.BoardDao;
import com.project.ohgym.dto.*;
import com.project.ohgym.util.PagingUtil;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Service
@Slf4j
public class BoardService {
    // DAO 들어갈 자리
    @Autowired
    private BoardDao bDao;

    private ModelAndView mv;

    private int listCnt = 5;

    // 게시판 리스트 출력
    public ModelAndView bShowList(SearchDto search, HttpSession session) {
        log.info("bShowList()");
        mv = new ModelAndView();

        int num = search.getPageNum();

        if (search.getListCnt() == 0) {
            search.setListCnt(listCnt);
        }
        if(num == 0) num = 1;
        search.setPageNum((num - 1) * search.getListCnt()); // 보여질 게시글 목록의 수

        List<BoardDto> bList = bDao.bShowList(search);
        mv.addObject("bList", bList);

        //  페이징 처리
        search.setPageNum(num);
        String pageHtml = getPaging(search);
        mv.addObject("paging", pageHtml);

        session.setAttribute("pageNum", num);
        if (search.getColname() != null) {
            session.setAttribute("search", search);
        } else {
            session.removeAttribute("search");
        }
        mv.setViewName("board");
        return mv;
    }

    // 페이징
    private String getPaging(SearchDto search) {
        log.info("getPaging()");
        String pageHtml = null;

        int maxNum = bDao.selectBoardCnt(search);
        int pageCnt = 5;
        String listName = "bShowList";
        if (search.getColname() != null) {
            listName += "?colname=" + search.getColname() + "&keyword=" + search.getKeyword() + "&";
        } else {
            listName += "?";
        }
        PagingUtil paging = new PagingUtil(maxNum, search.getPageNum(), search.getListCnt(), pageCnt, listName);
        pageHtml = paging.makePaging();
        return pageHtml;
    }

    // 입력
    public String bWrite(BoardDto board, RedirectAttributes rttr) {
        log.info("bWrite()");
        String view = null;
        String msg = null;
        try {
            bDao.bWrite(board);
            log.info("게시글 번호 : " + board.getBoardnum());
            view = "redirect:board?pageNum=1";
            msg = "글 작성 성공";
        } catch (Exception e) {
            e.printStackTrace();
            view = "redirect:boardWrite";
            msg = "글 작성 실패";
        }
        rttr.addFlashAttribute("msg", msg);
        return view;
    }

    // 헬스장 입력
    public String bGWrite(BoardDto board, RedirectAttributes rttr) {
        log.info("bGWrite()");
        String view = null;
        String msg = null;
        try {
            bDao.bGWrite(board);
            log.info("게시글 번호 : " + board.getBoardnum());
            view = "redirect:board?pageNum=1";
            msg = "글 작성 성공";
        } catch (Exception e) {
            e.printStackTrace();
            view = "redirect:boardWrite";
            msg = "글 작성 실패";
        }
        rttr.addFlashAttribute("msg", msg);
        return view;
    }

    // 상세보기 + 수정 글 불러오기
    public ModelAndView bContent(Integer boardnum) {
        log.info("bContent()");
        mv = new ModelAndView();

        // 글 내용
        BoardDto board = bDao.bContent(boardnum);
        // 댓글 목록
        List<ReplyDto> rList = bDao.selectReply(boardnum);
        // 조회수 수정
        bDao.updateView(boardnum);

        mv.addObject("bdetail", board);
        mv.addObject("rList", rList);
        mv.setViewName("boardDetail");
        return mv;
    }

    public ModelAndView bContentN(Integer boardnum) {
        log.info("bContentN()");
        mv = new ModelAndView();

        // 글 내용
        BoardDto board = bDao.bContent(boardnum);
        // 댓글 목록
        List<ReplyDto> rList = bDao.selectReply(boardnum);
        // 조회수 수정
        bDao.updateView(boardnum);

        mv.addObject("bdetail", board);
        mv.addObject("rList", rList);
        mv.setViewName("boardDetailM");
        return mv;
    }

    // 수정
    public String bUpdate(BoardDto board, RedirectAttributes rttr) {
        log.info("bUpdate()");
        String view = null;
        String msg = null;
        try {
            bDao.bUpdate(board);
            log.info("게시글 번호 : " + board.getBoardnum());
            view = "redirect:boardDetail?boardnum=" + board.getBoardnum();
            msg = "글 수정 성공";
        } catch (Exception e) {
            e.printStackTrace();
            view = "redirect:boardUpdate?boardnum=" + board.getBoardnum();
            msg = "글 작성 실패";
        }
        rttr.addFlashAttribute("msg", msg);
        return view;
    }

    // 삭제
    public String bDelete(Integer boardnum, HttpSession session, RedirectAttributes rttr) {
        log.info("bDelete()");
        String view = null;
        String msg = null;
        try {
            // 댓글 삭제
            bDao.deleteReply(boardnum);
            // 게시글 삭제
            bDao.bDelete(boardnum);
            view = "redirect:board";
            msg = "삭제 성공";
        } catch (Exception e) {
            e.printStackTrace();
            view = "redirect:boardDetail?boardnum=" + boardnum;
            msg = "삭제 실패";
        }
        rttr.addFlashAttribute("msg", msg);
        return view;
    }

    // 댓글
    public ReplyDto replyInsert(ReplyDto reply){
        log.info("replyInsert()");
        try{
            bDao.replyInsert(reply); // 댓글 저장
            // DB에 저장된 댓글 가져오기
            reply = bDao.selectLastReply(reply.getRnum());
        } catch (Exception e){
            e.printStackTrace();
            reply = null;
        }
        return reply;
    }

    public ReplyDto replyGymInsert(ReplyDto reply) {
        log.info("replyGymInsert()");
        try{
            bDao.replyGymInsert(reply); // 댓글 저장
            // DB에 저장된 댓글 가져오기
            reply = bDao.selectLastReply(reply.getRnum());
        } catch (Exception e){
            e.printStackTrace();
            reply = null;
        }
        return reply;
    }


}
