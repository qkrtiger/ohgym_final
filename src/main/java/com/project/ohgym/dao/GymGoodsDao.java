package com.project.ohgym.dao;

import com.project.ohgym.dto.GymDto;
import com.project.ohgym.dto.GymGoodsDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface GymGoodsDao {
    //헬스장 정보 가져오기
    GymDto selectGym(Integer gymnum);

    //헬스장 상품 정보 목록 가져오기
    List<GymGoodsDto> selectGymGoods(Integer gymnum);

    //헬스장 일일권 상품 정보 가져오기
    GymGoodsDto selectDailyGymGoods(Integer gymnum);

    //GymGoodsDto selectGymGoods(Integer gymnum);
}
