package com.project.ohgym.dao;

import com.project.ohgym.dto.GmListDto;
import com.project.ohgym.dto.MemberDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MemberDao {
    int idCheck(String mId);

    int nicknameCheck(String mnickname);

    int mailDoubleCheck(String mmail);

    void memberInsert(MemberDto member);

    String idFind(String mmail);

    String loginFind(MemberDto member);

    void passChange(MemberDto member);

    //회원의 비밀번호 검색 메소드
    String selectPass(String mid);

    //회원 정보를 가져오는 메소드(form memeber 테이블
    MemberDto selectMember(String mid);

}
