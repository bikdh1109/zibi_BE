package org.scoula.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.scoula.dto.GetRecentChecksDTO;
import org.scoula.dto.RecentCheckDTO;
import org.scoula.mapper.RecentCheckMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class RecentCheckService {
    private final RecentCheckMapper mapper;

    public void insertRecentCheck(int usersIdx, String pblancNo,String houseType) {
        // 1) 필수값 검증
        if (usersIdx <= 0) {
            throw new IllegalArgumentException("userIdx가 비어 있을 수 없습니다.");
        }
        if (pblancNo == null || pblancNo.trim().isEmpty()) {
            throw new IllegalArgumentException("pblancNo는 비어 있을 수 없습니다.");
        }
        if (houseType == null || houseType.trim().isEmpty()) {
            throw new IllegalArgumentException("houseType은 비어 있을 수 없습니다.");
        }

        RecentCheckDTO dto = RecentCheckDTO.builder()
                .usersIdx(usersIdx)
                .pblancNo(pblancNo)
                .houseType(houseType)
                .build();

        try {
            mapper.insertRecentCheck(dto);
        } catch (Exception e) {
            log.error("최근 본 공고 저장 실패 - userIdx: {}, pblancNo: {}", usersIdx, pblancNo, e);
            throw new RuntimeException("최근 본 공고 저장 중 오류가 발생했습니다.", e);
        }

    }

    public List<GetRecentChecksDTO> getRecentChecks(Integer usersIdx) {
        if (usersIdx == null || usersIdx <= 0) {
            throw new IllegalArgumentException("userIdx가 비어 있거나 0 이하입니다.");
        }
        try {
            return mapper.getRecentChecks(usersIdx);
        } catch (Exception e) {
            log.error("최근 본 공고 조회 실패 - userIdx: {}", usersIdx, e);
            throw new RuntimeException("최근 본 공고 조회 중 오류가 발생했습니다.", e);
        }
    }
}
