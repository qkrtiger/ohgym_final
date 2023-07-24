package com.project.ohgym.dao;

import com.project.ohgym.dto.BoardDto;
import com.project.ohgym.dto.ReplyDto;
import com.project.ohgym.dto.SearchDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardDao {
    // 게시글 목록 가져오는 메소드 선언
    List<BoardDto> bShowList(SearchDto search);
    // 게시글 저장
    void bWrite(BoardDto board);
    // 헬스장 게시글 저장
    void bGWrite(BoardDto board);
    // 게시글 1개 가져오기
    BoardDto bContent(Integer boardnum);
    // 게시글 수정
    void bUpdate(BoardDto board);
    // 개시글 삭제
    void bDelete(Integer boardnum);
    // 댓글 저장
    void replyInsert(ReplyDto reply);
    // 헬스장 댓글 저장
    void replyGymInsert(ReplyDto reply);
    // 추가한 댓글 가져오는 메소드 선언
    ReplyDto selectLastReply(int boardnum);
    // 댓글 목록
    List<ReplyDto> selectReply(Integer boardnum);
    // 게시글 개수 가져오는 메소드 선언
    int selectBoardCnt(SearchDto search);
    // 조회수 수정
    void updateView(Integer boardnum);
    // 댓글 목록 삭제 메소드 선언
    void deleteReply(Integer boardnum);
    //메인 게시글
    List<BoardDto> bestBoard();

}
