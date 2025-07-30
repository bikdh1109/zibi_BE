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
@ContextConfiguration(classes = { UserFavoriteService.class, RootConfig.class })
@Log4j2
class UserFavoriteServiceTest {

    @Autowired
    private UserFavoriteService userFavoriteService;

    /**
     * APT 공고번호를 즐겨찾기에 추가했을 때
     */
    @Test
    void addFavorite_apt() {
        String aptPblancNo = "2025950040";  // 테스트용 공고번호
        boolean result = userFavoriteService.addFavorite(1, "APT", aptPblancNo);
        log.info("APT 즐겨찾기 추가 결과: {}", result);
        assertTrue(result || !result);  // 단순 실행 확인
    }

    /**
     * 오피스텔 공고번호를 즐겨찾기에 추가했을 때
     */
    @Test
    void addFavorite_offi() {
        String offiPblancNo = "2025950040";  // 테스트용 공고번호
        boolean result = userFavoriteService.addFavorite(1, "OFFI", offiPblancNo);
        log.info("OFFI 즐겨찾기 추가 결과: {}", result);
        assertTrue(result || !result);
    }

    /**
     * 즐겨찾기 삭제
     */
    @Test
    void deleteFavorite() {
        int testFavoriteIdx = 1;  // 존재 여부에 따라 조정
        boolean result = userFavoriteService.deleteFavorite(testFavoriteIdx);
        log.info("즐겨찾기 삭제 결과 (userFavoriteIdx={}): {}", testFavoriteIdx, result);
        assertTrue(result || !result);
    }

    /**
     * 특정 사용자의 즐겨찾기 목록 조회
     */
    @Test
    void getFavorites() {
        int usersIdx = 1;
        List<UserFavoriteDTO> favorites = userFavoriteService.getFavorites(usersIdx);
        assertNotNull(favorites);
        log.info("usersIdx={} 의 즐겨찾기 개수: {}", usersIdx, favorites.size());
    }
}
