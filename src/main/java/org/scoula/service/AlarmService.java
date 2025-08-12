package org.scoula.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.scoula.mapper.AccountMapper;
import org.scoula.mapper.UserMapper;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class AlarmService {
    private final AccountMapper accountMapper;

    public void saveFcmToken(Integer userIdx, String fcmToken) {
        if (userIdx == null || userIdx <= 0) {
            throw new IllegalArgumentException("userIdx는 필수이며 0보다 커야 합니다.");
        }
        if (fcmToken == null || fcmToken.trim().isEmpty()) {
            throw new IllegalArgumentException("fcmToken은 비어 있을 수 없습니다.");
        }

        try {
            int updatedRows = accountMapper.updateFcmToken(userIdx, fcmToken);
            if (updatedRows == 0) {
                throw new IllegalStateException("해당 userIdx에 해당하는 사용자가 없습니다.");
            }

        } catch (Exception e) {
            log.error("FCM 토큰 저장 중 오류 발생: userIdx={}, token={}", userIdx, fcmToken, e);
            throw new RuntimeException("FCM 토큰 저장 중 오류가 발생했습니다.", e);
        }
    }

}
