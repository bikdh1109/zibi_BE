package org.scoula.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.scoula.dto.AlarmRequestDTO;
import org.scoula.mapper.UserMapper;
import org.scoula.security.util.JwtProcessor;
import org.scoula.service.AlarmService;
import org.scoula.util.TokenUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/v1/api")
@Log4j2
@RequiredArgsConstructor
public class AlarmController {
    private final AlarmService alarmService;
    private final TokenUtils tokenUtils;
    private final JwtProcessor jwtProcessor;
    private final UserMapper userMapper;

    @PutMapping("/token")
    public ResponseEntity<?> saveToken(
            @RequestHeader("Authorization") String bearerToken,
            @RequestBody AlarmRequestDTO body
    ) {
        try {
            if (body == null || body.getFcmToken() == null || body.getFcmToken().isBlank()) {
                return ResponseEntity.badRequest().body(Map.of("error", "fcmToken이 비어있습니다."));
            }

            String accessToken = tokenUtils.extractAccessToken(bearerToken);
            String userId = jwtProcessor.getUsername(accessToken);
            int userIdx = userMapper.findUserIdxByUserId(userId);

            if (userIdx <= 0) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("error", "유효하지 않은 사용자입니다."));
            }

            alarmService.saveFcmToken(userIdx, body.getFcmToken());
            log.info("Save Token - userIdx={}, token={}", userIdx, body.getFcmToken());

            return ResponseEntity.ok(Map.of("message", "FCM 토큰 저장 완료"));
        } catch (Exception e) {
            log.error("FCM 토큰 저장 중 오류 발생", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "서버 내부 오류"));
        }
    }

}
