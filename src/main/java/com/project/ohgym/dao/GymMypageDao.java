package com.project.ohgym.dao;

import com.project.ohgym.dto.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface GymMypageDao {
    GymDto selectGym(Integer gymnum);

    //헬스장 프로필 가져오는 메소드
    void gImage(GymDto image);

    int numCheck(int gnum);

    int selectGymnumCk(Integer gymnum);

    String selectPass(String gid);

    List<GymGoodsDto> gymGoods(Integer gymnum);

    void updateAddData(GymDto gym);

    GymCmDto gmcCheck(Integer gymnum);

    void deleteGymTrainer(Integer membernum);

    void updateGymConvenient(GymConvenientDto gConv);

    void updateGymMachine(GymMachineDto gMach);


    List<GymMemberDto> selectTraomer(Integer gymnum);


    GymMemberDto selectGmList(GymMemberDto gym);

    //헬스장 결제내역 가져오는 메소드

    List<MPayDto> GetgMypayList(SearchDto search);

    List<MPayDto> selectedgMpay(SearchDto search);

    GymDto selectgPay(SearchDto search);

    int selectgMPayCnt(SearchDto search);

    int selectgMpayCount(SearchDto search);

    //헬스장 상품정보 가져오는 메소드

    List<GymGoodsDto> GetgymGoods(Integer membernum);

    //헬스장 상품정보 등록하는 메소드
    void insertgGoodsSave(GymGoodsDto gymgoods);

    //헬스장 상품정보 삭제하는 메소드

    void gMGoodsDelete(int ggoodsnum);

    //헬스장 상품정보 활성화/비활성화 관리하는 메소드
    void gGoodsOnOff(GymGoodsDto gymgoods);

    GymGoodsDto selectGGoods(int ggoodsnum);

    List<ReviewDto> GetReviewList(SearchDto search);
}
