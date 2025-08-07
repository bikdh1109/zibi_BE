package org.scoula.dto;

import lombok.Data;

@Data
public class AllHouseListDTO {
    private String houseNm;
    private String hssplyAdres;
    private String pblancNo;
    private String applicationPeriod;
    private String si;
    private String sigungu;
    private String houseType;
    private Integer minArea;
    private Integer maxArea;
    private Long minPrice;
    private Long maxPrice;
    private Double latitude;
    private Double longitude;
}
