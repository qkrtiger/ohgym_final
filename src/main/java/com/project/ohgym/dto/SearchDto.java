package com.project.ohgym.dto;

import lombok.Data;

@Data
public class SearchDto {
    private String colname; // 검색할 DB 컬러명 저장
    private String keyword; // 검색 키워드 저장
    private int pageNum; // 페이지 번호
    private int listCnt; // 페이지 당 보여질 개시글 개수 저장
    private int membernum;
    private int gymnum;
    private String mpaynum;
    private String mpayType;
}
