package com.project.ohgym.dto;

import lombok.Data;

import java.util.Date;

@Data
public class MemberDto {
    private int membernum;
    private String mid;
    private String mpass;
    private String mmail;
    private String mname;
    private Date mbirth;
    private String mgender;
    private String mphone;
    private String mnickname;
    private String moriname;
    private String msysname;
    private String mresolution;
    private String mregion;
    private Date mdate;
    private int gymnum;
    private int trainnum;
    private String tcareer;
    private String tlicence;
    private String tpr;
    private String toriname;
    private String tsysname;
}

