package org.scoula.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.scoula.dto.UserFavoriteDTO;

import java.util.List;

@Mapper
public interface UserFavoriteMapper {
    /** 단일 즐겨찾기 추가 */
    int insertUserFavorite(UserFavoriteDTO favorite);


//    /** 특정 사용자의 즐겨찾기 리스트 조회 */
//    List<UserFavoriteDTO> findFavoritesByUsersIdx(int usersIdx);
//
//    /** 특정 즐겨찾기 삭제 */
//    int deleteUserFavorite(int userFavoriteIdx);

    /** APT 공고번호 기준 중복 체크 */
    int countByUsersIdxAndAptPblanc(
            @Param("usersIdx")   int usersIdx,
            @Param("aptPblanc") String aptPblanc
    );

    /** 오피스텔 공고번호 기준 중복 체크 */
    int countByUsersIdxAndOfficePblanc(
            @Param("usersIdx")      int usersIdx,
            @Param("officePblanc") String officePblanc
    );

    int deleteByUsersIdxAndAptPblanc(@Param("usersIdx") int usersIdx, @Param("aptPblanc") String aptPblanc);

    int deleteByUsersIdxAndOfficePblanc(@Param("usersIdx") int usersIdx,@Param("officePblanc") String officePblanc);
}
