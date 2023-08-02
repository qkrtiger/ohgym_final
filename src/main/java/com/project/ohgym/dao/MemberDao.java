package com.project.ohgym.dao;

import com.project.ohgym.dto.GmListDto;
import com.project.ohgym.dto.MemberDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;


@Mapper
public interface MemberDao {

    //이미 가입된 회원인지 확인하는 메소드
    MemberDto findkakao(HashMap<String, Object> userInfo);

    //카카오 로그인 회원정보 저장
    void kakaoinsert(HashMap<String, Object> userInfo);

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
