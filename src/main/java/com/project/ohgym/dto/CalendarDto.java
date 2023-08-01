package com.project.ohgym.dto;

import lombok.Data;

@Data
public class CalendarDto {
    private int calendarnum;
    private int membernum;
    private String title;
    private String start;
    private String end;
    private boolean allDay;
}
