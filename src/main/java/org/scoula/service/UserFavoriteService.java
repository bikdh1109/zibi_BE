package org.scoula.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.scoula.dto.UserFavoriteDTO;
import org.scoula.mapper.UserFavoriteMapper;
import org.scoula.mapper.UserMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserFavoriteService {
    private final UserFavoriteMapper userFavoriteMapper;

    // 즐겨찾기 추가
    public boolean addFavorite(int usersIdx, String houseType, int noticeIdx) {
        try {
            UserFavoriteDTO favorite = new UserFavoriteDTO();
            favorite.setUsersIdx(usersIdx);
            if ("APT".equals(houseType) || "신혼희망타운".equals(houseType)) {
                if (isFavoriteAPT(usersIdx, noticeIdx)) {
                    log.info("APT 즐겨찾기가 이미 존재합니다. usersIdx={}, noticeIdx={}", usersIdx, noticeIdx);
                    return false;  // 이미 존재
                }
                favorite.setAptIdx(noticeIdx);
                favorite.setOffiIdx(null);
            } else {
                if (isFavoriteOFFI(usersIdx, noticeIdx)) {
                    log.info("OFFI 즐겨찾기가 이미 존재합니다. usersIdx={}, noticeIdx={}", usersIdx, noticeIdx);
                    return false;  // 이미 존재
                }
                favorite.setOffiIdx(noticeIdx);
                favorite.setAptIdx(null);
            }

            if (userFavoriteMapper.insertUserFavorite(favorite) == 1) {
                return true;
            }
            return false;

        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }

    // 즐겨찾기 해제
    public boolean deleteFavorite(int userFavoriteIdx) {
        try {
            if (userFavoriteMapper.deleteUserFavorite(userFavoriteIdx) == 1) {
                return true;
            }
            return false;
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }

    // 즐겨찾기 목록 조회
    public List<UserFavoriteDTO> getFavorites(int usersIdx) {
        return userFavoriteMapper.findFavoritesByUsersIdx(usersIdx);
    }

    // 즐겨찾기 여부 확인
    public boolean isFavoriteAPT(int usersIdx, int noticeIdx) {
        return userFavoriteMapper.isFavoriteAPT(usersIdx, noticeIdx);
    }

    public boolean isFavoriteOFFI(int usersIdx, int noticeIdx) {
        return userFavoriteMapper.isFavoriteOFFI(usersIdx, noticeIdx);
    }
}