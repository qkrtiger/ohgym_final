package com.project.ohgym.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class BoardDto {
    private int boardnum;
    private int membernum;
    private String boardtitle;
    private String boardcontents;
    private Timestamp boarddate;
    private int boardcount;
    private String mnickname;
    private String msysname;
    private int gymnum;
    private String gname;
    private String gsysname;
}
