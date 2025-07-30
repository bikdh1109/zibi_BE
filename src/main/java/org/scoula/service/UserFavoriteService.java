package org.scoula.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.scoula.dto.UserFavoriteDTO;
import org.scoula.mapper.UserFavoriteMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserFavoriteService {
    private final UserFavoriteMapper mapper;

    /** 즐겨찾기 추가*/
    public boolean addFavorite(int usersIdx, String houseType, String pblancNo) {
        try {
            // 1) 중복 체크
            boolean exists;
            if ("APT".equals(houseType) || "신혼희망타운".equals(houseType)) {
                exists = mapper.countByUsersIdxAndAptPblanc(usersIdx, pblancNo) > 0;
            } else {
                exists = mapper.countByUsersIdxAndOfficePblanc(usersIdx, pblancNo) > 0;
            }
            if (exists) {
                log.info("{} 즐겨찾기가 이미 존재합니다. usersIdx={}, pblancNo={}", houseType, usersIdx, pblancNo);
                return false;
            }

            // 2) DTO 세팅
            UserFavoriteDTO fav = new UserFavoriteDTO();
            fav.setUsersIdx(usersIdx);
            if ("APT".equals(houseType) || "신혼희망타운".equals(houseType)) {
                fav.setAptPblanc(pblancNo);
                fav.setOfficePblanc(null);
            } else {
                fav.setOfficePblanc(pblancNo);
                fav.setAptPblanc(null);
            }

            // 3) 삽입 실행
            return mapper.insertUserFavorite(fav) == 1;
        } catch (Exception e) {
            log.error("즐겨찾기 추가 실패: {}", e.getMessage(), e);
            return false;
        }
    }

    /**즐겨찾기 해제*/
    public boolean deleteFavorite(int usersIdx, String houseType, String pblancNo) {
        try {
            if ("APT".equals(houseType) || "신혼희망타운".equals(houseType)) {
                return mapper.deleteByUsersIdxAndAptPblanc(usersIdx, pblancNo) == 1;
            } else {
                return mapper.deleteByUsersIdxAndOfficePblanc(usersIdx, pblancNo) == 1;
            }
        } catch (Exception e) {
            log.error("즐겨찾기 삭제 실패: {}", e.getMessage(), e);
            return false;
        }
    }

//
//    /**즐겨찾기 목록 조회*/
//    public List<UserFavoriteDTO> getFavorites(int usersIdx) {
//        return mapper.findFavoritesByUsersIdx(usersIdx);
//    }
}
