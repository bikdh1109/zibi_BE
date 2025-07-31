package org.scoula.dto.oauth;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class KakaoUserInfoDto {
    private Long kakaoId;
    private String email;
    private String nickname;
    private String name;
    private String birthday;      // 생일 (MMDD)
    private String birthyear;     // 출생연도 (YYYY)
    private String shippingAddress; // 배송지 정보 (주소 문자열 또는 JSON)
    private String token;
}
