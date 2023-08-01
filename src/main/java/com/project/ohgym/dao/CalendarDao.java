package com.project.ohgym.dao;

import com.project.ohgym.dto.CalendarDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CalendarDao {
    List<CalendarDto> cShowList(int membernum);

    void calInsert(CalendarDto cal);

    void deleteCal(Integer calendarnum);
}
