package org.scoula.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
@Data
public class OfficetelDetailDTO {
    private String houseManageNo;
    private String pblancNo;
    private String houseNm;
    private String houseSecd;
    private String houseSecdNm;
    private String houseDtlSecd;
    private String houseDtlSecdNm;
    private String searchHouseSecd;
    private String subscrptAreaCode;
    private String subscrptAreaCodeNm;
    private String hssplyAdres;
    private Integer totSuplyHshldco;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate rcritPblancDe;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate rceptBgnde;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate rceptEndde;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate przwnerPresnatnDe;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate cntrctCnclsBgnde;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate cntrctCnclsEndde;
    private String hmpgAdres;
    private String bsnsMbyNm;
    private String mdhsTelno;
    private String mvnPrearngeYm;
    private String pblancUrl;
    private List<OfficetelTypeDTO> officetelType;
}
