package com.project.ohgym.dao;

import com.project.ohgym.dto.MemberDto;
import com.project.ohgym.dto.ReviewDto;
import com.project.ohgym.dto.SearchDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReviewDao {

    //회원의 비밀번호 검색 메소드
    String selectPass(String mid);
    //회원 정보를 가져오는 메소드(from member 테이블)
    MemberDto selectMember(String mid);
    // 후기 입력
    void insertreview(ReviewDto review);
    // 후기 출력
    List<ReviewDto> rvList(SearchDto search);
    // 후기 갯수 가져오는 메소드 선언
    int selectReviewCnt(SearchDto search);

    void updatempay(ReviewDto review);

    void deleteReview(String mpayDel);
}
