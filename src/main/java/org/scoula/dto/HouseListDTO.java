package org.scoula.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class HouseListDTO {
    @JsonProperty("house_nm") // 공고명
    private String houseNm;
    @JsonProperty("hssply_adres") // 공급 주소
    private String hssplyAdres;
    @JsonProperty("application_period") // 접수 기간 문자열 (예: 2025.07.31 ~ 2025.08.05)
    private String applicationPeriod;
    @JsonProperty("suply_ar") // 공급 면적 (apt_type.suply_ar 또는 officetel_type.excluse_ar)
    private String suplyAr;
    @JsonProperty("lttot_top_amount") // 분양가 (apt_type.lttot_top_amount 또는 officetel_type.suply_amount)
    private String lttotTopAmount;
    @JsonProperty("house_type") // 공고유형 (APT, 신혼희망타운, 오피스텔, 도시형생활주택)
    private String houseType;
}
