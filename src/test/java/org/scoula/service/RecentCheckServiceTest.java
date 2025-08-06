package org.scoula.service;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.scoula.config.RootConfig;
import org.scoula.dto.GetRecentChecksDTO;
import org.scoula.dto.RecentCheckDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { RecentCheckService.class, RootConfig.class })
@Log4j2
public class RecentCheckServiceTest {
    @Autowired
    private RecentCheckService service;

    @Test
    @DisplayName("최근 본 공고 저장")
    public void testInsertAndGetRecentChecks() {
        // given
        int userIdx = 1;
        String pblancNo = "2025820011";
        String houseType = "신혼희망타운";

        // when
        service.insertRecentCheck(userIdx, pblancNo,houseType);
    }

    @Test
    @DisplayName("최근 본 공고 조회만 테스트")
    public void testGetRecentChecks() {
        // given
        int userIdx = 1;

        // when
        List<GetRecentChecksDTO> recentChecks = service.getRecentChecks(1);

        // 로그 출력
        recentChecks.forEach(dto -> log.info("조회 결과: {}", dto));
    }
}
