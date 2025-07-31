package org.scoula.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.scoula.dto.oauth.KakaoUserInfoDto;
import org.scoula.service.oauth.KakaoOauthService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/oauth/kakao")
public class KakaoLoginController {

    private final KakaoOauthService kakaoOauthService;

    /**
     * 카카오 로그인 후 리다이렉트되는 callback 엔드포인트
     * @param code 카카오에서 전달하는 인가 코드
     * @return KakaoUserInfoDto + JWT
     */
    @GetMapping("/callback")
    public ResponseEntity<KakaoUserInfoDto> kakaoLogin(@RequestParam("code") String code) {
        log.info("카카오 인가 코드 수신: {}", code);

        KakaoUserInfoDto userInfo = kakaoOauthService.processKakaoLogin(code);
        return ResponseEntity.ok(userInfo);
    }

    @GetMapping("/login")
    public String redirectToKakao() {
        return "redirect:https://kauth.kakao.com/oauth/authorize?client_id=53da207a5cc86b7ec03890c960d2937b&redirect_uri=http://localhost:8080/api/oauth/kakao/callback&response_type=code";
    }
}
