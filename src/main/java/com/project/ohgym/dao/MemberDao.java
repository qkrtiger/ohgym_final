package com.project.ohgym.dao;

import com.project.ohgym.dto.GmListDto;
import com.project.ohgym.dto.MemberDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface MemberDao {
    //회원정보 가져오는 메소드
    MemberDto selectMember(Integer membernum);

    //찜목록 메소드
    List<GmListDto> selectMarkedGyms(Integer membernum);

    //회원 프로필 가져오는 메소드
    void mImage(MemberDto image);
}
