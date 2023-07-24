package com.project.ohgym.dto;

import lombok.Data;

@Data
public class GymMachineDto {
    private int gymnum;
    private boolean gm_latpull;
    private boolean gm_shoulderpress;
    private boolean gm_citydraw;
    private boolean gm_legpress;
    private boolean gm_smithmachine;
    private boolean gm_lyinglegcurl;
    private boolean gm_chestfly;
    private boolean gm_hyperextension;
}
