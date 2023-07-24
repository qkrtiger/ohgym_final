package com.project.ohgym.dto;

import lombok.Data;

@Data
public class FilterViewDto {

    private String ggoodsname;

    private String mgender;

    private boolean gm_latpull;
    private boolean gm_shoulderpress;
    private boolean gm_citydraw;
    private boolean gm_legpress;
    private boolean gm_smithmachine;
    private boolean gm_lyinglegcurl;
    private boolean gm_chestfly;
    private boolean gm_hyperextension;

    private boolean gc_cloths;
    private boolean gc_towel;
    private boolean gc_wifi;
    private boolean gc_parking;
    private boolean gc_inbody;
    private boolean gc_sauna;

    private int gymnum;
    private String gname;
    private String glocation;
    private String goriname;
    private String gsysname;
}
