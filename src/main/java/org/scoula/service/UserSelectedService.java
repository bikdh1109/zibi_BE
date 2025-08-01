package org.scoula.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.scoula.dto.*;
import org.scoula.mapper.SelectedMapper;
import org.scoula.mapper.UserMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Log4j2
public class UserSelectedService {

    private final SelectedMapper selectedMapper;
    private final UserMapper userMapper;

    @Transactional
    public void saveAllPreferences(String userId, UserSelectedDTO dto) {
        int usersIdx = userMapper.findUserIdxByUserId(userId);

        selectedMapper.updateHomePrice(usersIdx, dto.getHomePrice());

        int userInfoIdx = selectedMapper.findUserInfoIdxByUserIdx(usersIdx);

        selectedMapper.deleteSelectedRegion(userInfoIdx);
        selectedMapper.deleteSelectedHomeSize(userInfoIdx);
        selectedMapper.deleteSelectedHomeType(userInfoIdx);

        for (RegionDTO region : dto.getSelectedRegion()) {
            selectedMapper.insertSelectedRegion(userInfoIdx, region);
        }

        for (HomeSizeDTO homeSize : dto.getSelectedHomesize()) {
            selectedMapper.insertSelectedHomeSize(userInfoIdx, homeSize);
        }

        for (HomeTypeDTO homeType : dto.getSelectedHometype()) {
            selectedMapper.insertSelectedHomeType(userInfoIdx, homeType);
        }
    }

    public UserSelectedDTO getUserSelected(String userId) {
        int usersIdx = userMapper.findUserIdxByUserId(userId);
        int userInfoIdx = selectedMapper.findUserInfoIdxByUserIdx(usersIdx);

        HomePriceDTO homePrice = selectedMapper.selectHomePriceByUserIdx(usersIdx);
        List<RegionDTO> regions = selectedMapper.selectSelectedRegion(userInfoIdx);
        List<HomeSizeDTO> homesizes = selectedMapper.selectSelectedHomesize(userInfoIdx);
        List<HomeTypeDTO> hometypes = selectedMapper.selectSelectedHometype(userInfoIdx);

        return new UserSelectedDTO(homePrice, regions, homesizes, hometypes);
    }

    public List<Map<String, Object>> getRecommendedNotices(String userId) {
        int usersIdx = userMapper.findUserIdxByUserId(userId);
        int userInfoIdx = selectedMapper.findUserInfoIdxByUserIdx(usersIdx);

        // 사용자 선호 조건 조회
        HomePriceDTO homePrice = selectedMapper.selectHomePriceByUserIdx(usersIdx);
        List<RegionDTO> regions = selectedMapper.selectSelectedRegion(userInfoIdx);
        List<HomeSizeDTO> homesizes = selectedMapper.selectSelectedHomesize(userInfoIdx);
        List<HomeTypeDTO> hometypes = selectedMapper.selectSelectedHometype(userInfoIdx);

        List<Map<String, Object>> combined = new ArrayList<>();

        // homeType 값에 따라 테이블 분기
        for (HomeTypeDTO homeType : hometypes) {
            if ("아파트".equals(homeType.getSelectedHouseSecd())) {
                combined.addAll(selectedMapper.findAptNotices(homePrice, regions, homesizes, hometypes));
            }
            if ("오피스텔".equals(homeType.getSelectedHouseSecd()) || "도시형생활주택".equals(homeType.getSelectedHouseSecd())) {
                combined.addAll(selectedMapper.findOfficetelNotices(homePrice, regions, homesizes, hometypes));
            }
        }

        return combined;
    }

}
