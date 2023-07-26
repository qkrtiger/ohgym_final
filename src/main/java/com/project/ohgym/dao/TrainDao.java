package com.project.ohgym.dao;

import com.project.ohgym.dto.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TrainDao {
    //회원정보 가져오는 메소드
    TrainerDto selectTrainer(Integer membernum);

    //트레이너 결제내역 가져오는 메소드
    List<MPayDto> tMPay(Integer tgoodsint, Integer membernum);

    //트레이너 상품정보 가져오는 메소드
    List<TrainGoodsDto> GettrainerGoods(Integer membernum);

    //트레이너 상품정보 등록하는 메소드
    void inserttMGoods(TrainGoodsDto traingoods);

    //트레이너 상품정보 삭제하는 메소드

    void tMGoodsDelete(Integer tgoodsint);

    //트레이너 상품정보 활성화/비활성화 관리하는 메소드
    void tMGoodsOnOff(TrainGoodsDto tgoodsint);

    TrainGoodsDto selectTGoods(int tgoodsint);

    // 헬스장 상세페이지
    List<TrainDto> getGoods(Integer gymnum);

    List<TrainGoodsDto> trainGoods(Integer membernum);

    void updateAddData(TrainerDto tImage);

    List<MaptrainDto> selectTrainList();
}
