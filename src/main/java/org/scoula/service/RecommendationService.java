package org.scoula.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.scoula.dto.*;
import org.scoula.mapper.*;
import org.scoula.security.util.JwtProcessor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@Log4j2
public class RecommendationService {
    private final PythonApiService pythonApiService;
    private final JwtProcessor jwtProcessor;
    private final UserMapper userMapper;
    private final UserInfoMapper userInfoMapper;
    private final HouseListMapper houseListMapper;
    private final AptMapper aptMapper;
    private final SelectedMapper selectedMapper;

    public HashMap<Integer, HouseListDTO> wintProbability = new HashMap<>();
    public List<HouseListDTO> getRecommendationList(Integer usersIdx) {

        UserInfoDTO userInfo = userInfoMapper.getUserInfoByUsersIdx(usersIdx);

        PreferenceDTO preference = getPreferenceInfo(usersIdx, userInfo);
        List<RegionDTO> siGungu = preference.getSiGunGu();

        // 선택한 모든 선호 지역에 대한 청약 공고를 가져오기위해 반복문 사용
        for (RegionDTO region : siGungu) {
            // 선호 아파트 타입으로 아파트를 선택한 경우
            if (preference.getHomeType().contains("APT")) {
                List<HouseListDTO> aptHouseList = houseListMapper.getAptRecommendationList(region, preference);
                getAptReqDTO(usersIdx, aptHouseList, userInfo);
            }
            // 선호 매물 타입으로 오피스텔을 선택한 경우
            if (preference.getHomeType().contains("오피스텔")) {
                List<HouseListDTO> officetelHouseList = houseListMapper.getOfficetelRecommendationList(region, preference);
                getOfficetelReqDTO(usersIdx, officetelHouseList, userInfo);
            }
        }

        // 선호 정보에 맞는 청약 리스트 정렬
        Map<Integer, HouseListDTO> sortedMap = new TreeMap<>(wintProbability);
        List<HouseListDTO> winProbabilityList = new ArrayList<>(sortedMap.values());
        return winProbabilityList;
    }

    public void getAptReqDTO(int usersIdx, List<HouseListDTO> aptHouseList, UserInfoDTO userInfo) {

        for (HouseListDTO house : aptHouseList) {
            String pblancNo = house.getPblancNo();
            PythonAptRequestDTO aptReqDTO = new PythonAptRequestDTO();

            int aptIdx = aptMapper.findAptIdxByPblancNo(pblancNo);
            AptTypeDTO aptType = aptMapper.getAptType(aptIdx);

            aptReqDTO.setGnsplyHshldco(aptType.getSuplyHshldco());
            aptReqDTO.setSpsplyHshldco(aptType.getSpsplyHshldco());
            aptReqDTO.setSi(house.getSi());
            aptReqDTO.setSigungu(house.getSigungu());
            aptReqDTO.setTotSuplyHshldco(aptType.getSuplyHshldco() + aptType.getSpsplyHshldco());
            aptReqDTO.setSuplyHshldco(aptType.getSuplyHshldco() + aptType.getSpsplyHshldco());
            aptReqDTO.setHouseRank(selectedMapper.getUserRankByUserInfoIdx(userInfo.getUserInfoIdx()));
            aptReqDTO.setScore(userInfo.getTotalGaScore());

            if (userMapper.findUserRegionByIdx(usersIdx).startsWith(aptMapper.findRegionByAptPblancNo(pblancNo))) {
                aptReqDTO.setResideSecd(1);
            } else {
                aptReqDTO.setResideSecd(2);
            }
            Object probability = pythonApiService.requestPrediction(aptReqDTO).get("probability");
            wintProbability.put((Integer) probability, house);
        }
    }

    public void getOfficetelReqDTO(int usersIdx, List<HouseListDTO> officetelHouseList, UserInfoDTO userInfo) {
        for (HouseListDTO house : officetelHouseList) {
            String pblancNo = house.getPblancNo();
            PythonOfficetelRequestDTO officetelReqDTO = new PythonOfficetelRequestDTO();
            int officetelIdx = aptMapper.findOfficetelIdxByPblancNo(pblancNo);
            OfficetelDetailDTO officetelDetail = aptMapper.getOfficetelDetails(pblancNo);
            OfficetelTypeDTO officeType = aptMapper.getOfficetelType(officetelIdx);
            officetelReqDTO.setTotSuplyHshldco(officetelDetail.getTotSuplyHshldco());
            officetelReqDTO.setSuplyHshldco(officeType.getSuplyHshldco());
            if (userMapper.findUserRegionByIdx(usersIdx).startsWith(aptMapper.findRegionByOfficetelPblancNo(pblancNo))) {
                officetelReqDTO.setResideSecd(1);
            } else {
                officetelReqDTO.setResideSecd(2);
            }

            Object probability = pythonApiService.requestOfficetelPrediction(officetelReqDTO).get("probability");
            wintProbability.put((Integer) probability, house);
        }
    }

    public PreferenceDTO getPreferenceInfo (int usersIdx, UserInfoDTO userInfo) {
        int userInfoIdx = userInfo.getUserInfoIdx();
        List<RegionDTO> siGunGu = userInfoMapper.findSelectedSiGunGuByUserInfoIdx(userInfoIdx);
        int minHomeSize = userInfoMapper.findSelectedMinHomeSizeByUserInfoIdx(userInfoIdx);
        int maxHomeSize = userInfoMapper.findSelectedMaxHomeSizeByUserInfoIdx(userInfoIdx);
        List<String> selectedHomeType = userInfoMapper.findSelectedHomeTypeByUserInfoIdx(userInfoIdx);

        PreferenceDTO preference = new PreferenceDTO();

        preference.setUsersIdx(usersIdx);
        preference.setSiGunGu(siGunGu);
        preference.setMinHomeSize(minHomeSize);
        preference.setMaxHomeSize(maxHomeSize);
        preference.setHopeMaxPrice(userInfo.getHopeMaxPrice());
        preference.setHopeMinPrice(userInfo.getHopeMinPrice());
        preference.setHomeType(selectedHomeType);
        preference.setMaritalStatus(userInfo.getMaritalStatus());
        preference.setTotalGaScore(userInfo.getTotalGaScore());

        return preference;
    }
}