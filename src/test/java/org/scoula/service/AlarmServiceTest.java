package org.scoula.service;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.scoula.config.RootConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { AlarmService.class, RootConfig.class })
@Log4j2
class AlarmServiceTest {

    @Autowired
    private AlarmService alarmService;

    @Test
    void testCreateNewNotice() {
        Long alarmIdx = alarmService.createNewNotice(
                "테스트 공고",
                "테스트 내용",
                "https://example.com",
                43
        );
        log.info("생성된 NEW_NOTICE 알람 ID = {}", alarmIdx);
    }

    @Test
    void testCreateApplicationStart() {
        Long alarmIdx = alarmService.createApplicationStart(
                "접수 시작",
                "접수 시작 안내",
                "https://apply.com",
                43
        );
        log.info("생성된 APPLICATION_START 알람 ID = {}", alarmIdx);
    }

    @Test
    void testCreateDepositUnpaid() {
        Long alarmIdx = alarmService.createDepositUnpaid(
                "예치금 미납",
                "예치금이 미납되었습니다.",
                43
        );
        log.info("생성된 DEPOSIT_UNPAID 알람 ID = {}", alarmIdx);
    }
}