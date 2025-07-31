package org.scoula.service.oauth;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {KakaoOauthService.class})
class KakaoOauthServiceTest {

    @Autowired
    private KakaoOauthService kakaoOauthService;
    @Test
    void processKakaoLogin() {

    }

    @Test
    void testProcessKakaoLogin() {
    }

    @Test
    void getAccessToken() {
    }

    @Test
    void getUserInfo() {
    }

    @Test
    void processKakaoUser() {
    }
}