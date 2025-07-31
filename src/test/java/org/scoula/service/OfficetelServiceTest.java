package org.scoula.service;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.scoula.config.RootConfig;
import org.scoula.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { OfficetelService.class, RootConfig.class }) // AptService만 테스트
@Log4j2
class OfficetelServiceTest {

    @Autowired
    private OfficetelService officetelService;

    @Test
    void fetchAptData() {
        OfficetelResponseDTO response = officetelService
                .fetchOfficetelData(1, 1);
        log.info("전체 응답 {}",response);

        if (response != null && response.getData() != null) {
            for (OfficetelDTO officetel : response.getData()) {
                log.info("Apt: {} \n", officetel);
            }
        }
    }

    @Test
    void getAllOfficetelData() {
        OfficetelResponseDTO response = officetelService.fetchOfficetelData(1,1);
        Integer match_count = response.getMatchCount();
        log.info("Match!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!{} : ",match_count);

        response = officetelService.fetchOfficetelData(1,match_count);
        if (response != null && response.getData() != null) {
            for (OfficetelDTO officetel : response.getData()) {
                log.info("Apt: {} \n", officetel);
            }
        }
    }

    @Test
    void saveOfficetelData() {
        OfficetelResponseDTO response = officetelService.fetchOfficetelData(1,1);
        Integer match_count = response.getMatchCount();
        log.info("Match!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!{}",match_count);

        if (match_count > 1) {
            response = officetelService.fetchOfficetelData(1, match_count);
        }
        officetelService.saveOfficetelData(response);
        if (response != null && response.getData() != null) {
            for (OfficetelDTO officetel : response.getData()) {
                log.info("저장된 APT: {}", officetel);
            }
        } else {
            fail("response 또는 data가 null입니다.");
        }
    }

    @Test
     void getOfficeTypeData() {
       OfficetelTypeResponseDTO response = officetelService.fetchOfficetelTypeData("2025950037");
        log.info("전체 응답 {}",response);

        if (response != null && response.getData() != null) {
            for (OfficetelTypeDTO apt : response.getData()) {
                log.info("OfficetelType: {} \n", apt);
            }
        }
    }


    @Test
    void getAllAptTypeData() {
        OfficetelTypeResponseDTO response = officetelService.fetchOfficetelTypeData("2025950037");
        Integer match_count = response.getMatchCount();
        log.info("Current!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! : {}",match_count);
    }

    @Test
    void getallIdx() {
        List<OfficetelIdxDTO> response =  officetelService.getOfficetelIdxAndHouseManageNo();
        response.forEach(dto -> log.info(dto.toString()));
    }

    @Test
    void saveOfficetelTypes (){
        officetelService.saveOfficetelTypes();
    }

    @Test
    void syncOfficetelData() {
        officetelService.syncOfficetelData();
    }

    @Test
    void deleteOfficetelData() {
        officetelService.deleteOldOfficetelDataBeforeThisMonth();
    }

    @Test
    void getAptDetail() {
        OfficetelDetailDTO detail = officetelService.getOfficetelDetail("2025950040");
        log.info("OfficetelDetailDTO  : {}",detail.toString());

    }


}