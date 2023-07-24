package com.project.ohgym.dao;

import com.project.ohgym.dto.FilterViewDto;
import com.project.ohgym.dto.GymDetailDto;
import com.project.ohgym.dto.GymDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface GymDao {

    // 헬스장 목록
    List<GymDto> hGymList(Integer membernum);
    // 헬스장 지역 검색창
    List<GymDto> hGymPlaceList(String glocation);
    // 헬스장 도시 검색창
    List<GymDto> hSeachGymOutput(String gcity);
    // 검색필터
    List<FilterViewDto> hGymFilteraList(Map<String, String> fMap);

    // 헬스장 상세페이지
    GymDetailDto getGymPage(Integer gymnum);
    GymDetailDto getGymConven(Integer gymnum);
    GymDetailDto getGymMechin(Integer gymnum);
    List<GymDetailDto> getGoods(Integer gymnum);
    List<GymDetailDto> getTimg(Integer gymnum);
    int getGymMark(Integer gymnum, Integer membernum);
    int getlen(Integer gymnum);
    float getavg(Integer gymnum);

    // 찜
    void insertMark(Integer membernum, Integer gymnum);
    void deleteMark(Integer membernum, Integer gymnum);

    List<GymDto> nonMbGymList();

    //신규 헬스장
    List<GymDto> newGym();

    // 메인 셀렉트 박스 검색
    List<GymDto> GymPlaceList(String glocation);

    List<GymDto> searchList(String gname);

    List<GymDto> getoneList(String goods);

    //아이콘 검색
    List<GymDto> getWomanList(String wgender);

    List<GymDto> getManList(String mgender);

    //헬스장 키워드로 검색
    List<GymDto> hSearch(String gname);
}
