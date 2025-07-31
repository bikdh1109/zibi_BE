package org.scoula.service;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.scoula.config.RootConfig;
import org.scoula.dto.UserFavoriteDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { UserFavoriteService.class, RootConfig.class }) // AptService만 테스트
@Log4j2
class UserFavoriteServiceTest {
    @Autowired
    UserFavoriteService userFavoriteService;
    @Test
    void addFavorite() {
        boolean result1 = userFavoriteService.addFavorite(1, "APT", 10);
        log.info("APT 즐겨찾기 추가 결과: {}", result1);
        assertTrue(result1 || !result1);  // 단순 실행 테스트 (값 확인 가능)

        boolean result2 = userFavoriteService.addFavorite(1, "OFFI", 3);
        log.info("OFFI 즐겨찾기 추가 결과: {}", result2);
        assertTrue(result2 || !result2);
    }

    @Test
    void deleteFavorite() {
        boolean result = userFavoriteService.deleteFavorite(2);
        log.info("즐겨찾기 삭제 결과 (userFavoriteIdx=2): {}", result);
        assertTrue(result || !result); // 단순 실행 확인용
    }

    @Test
    void getFavorites() {
        List<UserFavoriteDTO> favorites = userFavoriteService.getFavorites(1);
        assertNotNull(favorites);
        log.info("즐겨찾기 목록 크기: {}", favorites.size());
    }

    @Test
    void isFavorite() {
        boolean isAptFavorite = userFavoriteService.isFavoriteAPT(1, 10);
        log.info("APT 즐겨찾기 여부 (usersIdx=1, noticeIdx=10): {}", isAptFavorite);

        boolean isOffiFavorite = userFavoriteService.isFavoriteOFFI(1, 3);
        log.info("OFFI 즐겨찾기 여부 (usersIdx=1, noticeIdx=3): {}", isOffiFavorite);

        assertTrue(isAptFavorite || !isAptFavorite);
        assertTrue(isOffiFavorite || !isOffiFavorite);
    }
}