package org.scoula.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class AptDetailDTO {
    private String houseManageNo;
    private String pblancNo;
    private String houseNm;
    private String houseSecd;
    private String houseSecdNm;
    private String houseDtlSecd;
    private String houseDtlSecdNm;
    private String rentSecd;
    private String rentSecdNm;
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
    private LocalDate spsplyRceptBgnde;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate spsplyRceptEndde;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate gnrlRnk1CrspareaRcptde;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate gnrlRnk1CrspareaEndde;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate gnrlRnk1EtcAreaRcptde;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate gnrlRnk1EtcAreaEndde;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate gnrlRnk2CrspareaRcptde;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate gnrlRnk2CrspareaEndde;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate gnrlRnk2EtcAreaRcptde;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate gnrlRnk2EtcAreaEndde;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate przwnerPresnatnDe;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate cntrctCnclsBgnde;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate cntrctCnclsEndde;
    private String hmpgAdres;
    private String cnstrctEntrpsNm;
    private String mdhsTelno;
    private String bsnsMbyNm;
    private String mvnPrearngeYm;
    private String specltRdnEarthAt;
    private String mdatTrgetAreaSecd;
    private String parcprcUlsAt;
    private String pblancUrl;
    private List<AptTypeDTO> aptType;
}
