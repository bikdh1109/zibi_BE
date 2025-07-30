package org.scoula.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.scoula.dto.FavoriteRequestDTO;
import org.scoula.dto.UserFavoriteDTO;
import org.scoula.dto.swagger.Auth.SwaggerPasswordChangeRequestDTO;
import org.scoula.mapper.UserMapper;
import org.scoula.security.account.mapper.UserDetailsMapper;
import org.scoula.security.util.JwtProcessor;
import org.scoula.service.UserFavoriteService;
import org.scoula.service.UserService;
import org.scoula.util.TokenUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/v1/me/favorite")
@RequiredArgsConstructor
public class UserFavoriteController {
    private final UserMapper userMapper;
    private final UserFavoriteService userFavoriteService;
    private final JwtProcessor jwtProcessor;
    private final TokenUtils tokenUtils;
    private final UserService userService;
    public static final String BEARER_PREFIX = "Bearer ";

    // 즐겨찾기 추가
    @PostMapping(produces = "application/json; charset=UTF-8")
    public ResponseEntity<?> addFavorite(@RequestHeader ("Authorization") String bearerToken , @RequestBody FavoriteRequestDTO favorite) {
        String accessToken = tokenUtils.extractAccessToken(bearerToken);
        String username = jwtProcessor.getUsername(accessToken);
        int usersIdx = userMapper.findUserIdxByUserId(username);
        favorite.setUsersIdx(usersIdx);

        boolean success = userFavoriteService.addFavorite(favorite.getUsersIdx(), favorite.getHouseType(), favorite.getPblancNo());
        return success ? ResponseEntity.ok("즐겨찾기 추가 완료") :
                ResponseEntity.badRequest().body("이미 즐겨찾기에 존재합니다.");
    }
    // 즐겨찾기 삭제
    @DeleteMapping(produces = "application/json; charset=UTF-8")
    public ResponseEntity<String> removeFavorite(
            @RequestHeader("Authorization") String bearerToken,
            @RequestParam("house_type") String houseType,
            @RequestParam("pblanc_no") String pblancNo
    ) {
        try {
            String accessToken = tokenUtils.extractAccessToken(bearerToken);
            String username = jwtProcessor.getUsername(accessToken);
            int usersIdx = userMapper.findUserIdxByUserId(username);

            boolean success = userFavoriteService.deleteFavorite(usersIdx, houseType, pblancNo);

            return success ? ResponseEntity.ok("즐겨찾기 삭제 완료") : ResponseEntity.badRequest().body("즐겨찾기가 존재하지 않거나 삭제 실패");
        } catch (Exception e) {
            log.error("즐겨찾기 삭제 중 오류", e);
            return ResponseEntity.internalServerError().body("서버 오류 발생");
        }
    }

//    // 특정 사용자의 즐겨찾기 목록
//    @GetMapping("/list")
//    public ResponseEntity<?> getFavorites(@RequestHeader("Authorization") String bearerToken) {
//        try {
//            //  1. access 토큰 꺼내기
//            String accessToken = tokenUtils.extractAccessToken(bearerToken);
//            String username = jwtProcessor.getUsername(accessToken);
//            int usersIdx = userMapper.findUserIdxByUserId(username);
//
//            return ResponseEntity.ok(userFavoriteService.getFavorites(usersIdx));
//        } catch (Exception e) {
//            log.error("사용자 즐겨찾기 목록 읽는 중 오류", e);
//            return ResponseEntity.status(500).body(Map.of("error", "사용자 즐겨찾기 목록 읽는 중 오류"));
//        }
//    }
//


}
