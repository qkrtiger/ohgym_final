package com.project.ohgym.dao;

import com.project.ohgym.dto.GymConvenientDto;
import com.project.ohgym.dto.GymDto;
import com.project.ohgym.dto.GymMachineDto;
import com.project.ohgym.dto.MemberDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LoginDao {


    int idCheck(String mid);

    int nicknameCheck(String mnickname);

    int mailDoubleCheck(String mmail);

    void memberInsert(MemberDto member);

    int gidCheck(String gid);

    void gymInsert(GymDto gym);

    String idFind(String mmail);

    String loginFind(MemberDto member);

    void passChange(MemberDto member);

    String findGym(GymDto gym);

    void passGymChange(GymDto gym);

    MemberDto selectMember(String mid);

    String mSelectPass(String mid);

    String gSelectPass(String gid);

    GymDto selectGym(String gid);

    void insertCheckC(GymConvenientDto gc);

    void insertCheckM(GymMachineDto gm);



}
