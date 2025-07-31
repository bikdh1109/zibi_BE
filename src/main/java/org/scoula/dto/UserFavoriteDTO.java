package org.scoula.dto;

import lombok.Data;

@Data
public class UserFavoriteDTO {
    private int userFavoriteIdx;      // PK
    private Integer usersIdx;         // 사용자 ID
    private Integer aptIdx;           // 아파트 공고 ID (nullable)
    private Integer offiIdx;          // 오피스텔 공고 ID (nullable)
    private Float predictedWinRate;   // 예측 당첨 확률
    private Float predictedCutoffScore; // 예측 커트라인 점수
}
