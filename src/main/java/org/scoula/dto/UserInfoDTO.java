package org.scoula.dto;

import lombok.Data;

@Data
public class UserInfoDTO {
    private int userInfoIdx;
    private int hopeMaxPrice;
    private int hopeMinPrice;
    private int totalGaScore;
    private int maritalStatus;
}
