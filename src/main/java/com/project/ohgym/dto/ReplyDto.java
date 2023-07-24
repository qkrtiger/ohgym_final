package com.project.ohgym.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class ReplyDto {
    private int rnum;
    private String rcontent;
    private String mnickname;
    private String gname;
    private int boardnum;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private Timestamp rdate;
}
