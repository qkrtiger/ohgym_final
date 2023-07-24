package com.project.ohgym.dao;

import com.project.ohgym.dto.GmListDto;
import com.project.ohgym.dto.MPayDto;
import com.project.ohgym.dto.MemberDto;
import com.project.ohgym.dto.SearchDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MemberMypageDao {
    //회원정보 가져오는 메소드
    MemberDto selectMember(Integer membernum);

    //찜목록 메소드
    List<GmListDto> selectMarkedGyms(Integer membernum);

    //회원 프로필 가져오는 메소드
    void mImage(MemberDto image);

    void mtChange(MemberDto member);

    //회원 결제내역 가져오는 메소드
    List<MPayDto> GetMypayList(SearchDto search);

    //로그인
    //회원의 비밀번호 검색 메소드
    String selectPass(String mid);
    //회원 정보를 가져오는 메소드(from member 테이블)
    MemberDto selectMemberid(String mid);
    //결제내역 조회(회원권, 일일권, PT)

    List<MPayDto> selectedMpay(SearchDto search);
    int selectMpayCount(SearchDto search);

    //결제내역 개수 가져오는 메소드
    int selectMPayCnt(SearchDto search);

    //searchDto를 활용해서 멤버 정보 가져오는 메소드
    MemberDto selectMemberSearch(SearchDto search);

    void updateData(MemberDto member);
}
