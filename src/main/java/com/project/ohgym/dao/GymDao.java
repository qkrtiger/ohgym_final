package com.project.ohgym.dao;

import com.project.ohgym.dto.GymDto;
import com.project.ohgym.dto.MemberDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GymDao {
    GymDto selectGym(Integer gymnum);

    //헬스장 프로필 가져오는 메소드
    void gImage(GymDto image);

    int numCheck(int gnum);
}
