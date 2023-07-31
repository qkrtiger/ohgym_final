package com.project.ohgym.dao;

import com.project.ohgym.dto.GymDto;
import com.project.ohgym.dto.TMemberDto;
import com.project.ohgym.dto.TrainDto;
import com.project.ohgym.dto.TrainGoodsDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TMemberDao {
    //트레이너 정보를 가져오는 메소드
    TrainDto selectTrainDetail(Integer membernum);

    //헬스장 정보를 가져오기 위한 메소드
    GymDto selectGymDetail(Integer tgoodsint);

    //PT 상품 정보를 가져오는 메소드
    List<TrainGoodsDto> selectTrainGoods(Integer membernum);

    //PT 상품 상세보기(트레이너 상품 결제 페이지)
    TrainGoodsDto selectTrainGoodsDetail(Integer tgoodsint);

    //헬스장 이름 가져오는 메소드
    GymDto selectGymName(Integer tgoodsint);

    //tgoodsint 값으로 트레이너 정보 가져오는 메소드
    TrainDto selectTrain(int tgoodsint);
}
