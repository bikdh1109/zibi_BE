package org.scoula.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.scoula.dto.UserFavoriteDTO;

import java.util.List;

@Mapper
public interface UserFavoriteMapper {
    // 즐겨찾기 추가
    int insertUserFavorite(UserFavoriteDTO favorite);

    // 특정 사용자의 즐겨찾기 리스트 조회
    List<UserFavoriteDTO> findFavoritesByUsersIdx(int usersIdx);

    // 특정 즐겨찾기 삭제
    int deleteUserFavorite(int userFavoriteIdx);

    // 아파트 즐겨찾기 여부
    boolean isFavoriteAPT(@Param("usersIdx") int usersIdx, @Param("noticeIdx") int noticeIdx);

    // 오피스텔 즐겨찾기 여부
    boolean isFavoriteOFFI(@Param("usersIdx") int usersIdx, @Param("noticeIdx") int noticeIdx);

}
