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
@ContextConfiguration(classes = { HouseService.class, RootConfig.class }) // AptService만 테스트
@Log4j2
class HouseServiceTest {
    @Autowired
    private HouseService houseService;

    @Test
    void getAllHousingList() {
        houseService.getAllHousingList().forEach(dto -> log.info(dto.toString()));
    }


}