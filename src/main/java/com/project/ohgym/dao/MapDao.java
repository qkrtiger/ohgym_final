package com.project.ohgym.dao;

import com.project.ohgym.dto.GymDto;
import com.project.ohgym.dto.MaptrainDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MapDao {

    List<GymDto> selectGymList();

    List<GymDto> selectSearchNameList(String gname);

    List<MaptrainDto> selectTrainList();
}
