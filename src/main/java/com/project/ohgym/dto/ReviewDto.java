package com.project.ohgym.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class ReviewDto {
    private int reviewnum;
    private String reviewcontents;
    private String mnickname;
    private int membernum;
    private String mpaynum;
    private int gymnum;
    private int reviewstar;
    @JsonFormat(pattern = "yy-MM-dd", timezone = "Asia/Seoul")
    private Timestamp reviewdate;
    private String msysname;
    private String ratingOptions;
    private float avg;
}
