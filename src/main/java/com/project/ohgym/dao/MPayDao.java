package com.project.ohgym.dao;

import com.project.ohgym.dto.MPayDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MPayDao {
    //헬스장 결제 정보를 저장하는 메소드
    void insertMPay(MPayDto mpay);
    //트레이너 결제 정보를 저장하는 메소드
    void insertTMPay(MPayDto mpay);
    //사용자의 결제 정보를 가져오는 메소드
    MPayDto selectPayment(String mpaynum);


}

