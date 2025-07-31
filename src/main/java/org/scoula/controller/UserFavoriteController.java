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
    @PostMapping
    public ResponseEntity<String> addFavorite(@RequestBody FavoriteRequestDTO favorite) {
        boolean success = userFavoriteService.addFavorite(favorite.getUsersIdx(), favorite.getHouseType(), favorite.getNoticeIdx());
        return success ? ResponseEntity.ok("즐겨찾기 추가 완료") :
                ResponseEntity.badRequest().body("이미 즐겨찾기에 존재합니다.");
    }

    // 특정 사용자의 즐겨찾기 목록
    @GetMapping("/list")
    public ResponseEntity<?> getFavorites(@RequestHeader("Authorization") String bearerToken) {
        try {
            //  1. access 토큰 꺼내기
            String accessToken = null;
            if (bearerToken != null && bearerToken.startsWith(BEARER_PREFIX)) {
                accessToken = bearerToken.substring(BEARER_PREFIX.length());}
            else {
                return ResponseEntity.status(400).body(Map.of("error", "Authorization 헤더가 유효하지 않습니다"));}

            // 2. 유효성 검사하기
            if (!jwtProcessor.validateToken(accessToken)) {
                return ResponseEntity.status(401).body(Map.of("error", "유효하지 않은 Access Token"));}

            // 3. Refresh Token 삭제
            String username = jwtProcessor.getUsername(accessToken);
            int usersIdx = userMapper.findUserIdxByUserId(username);

            return ResponseEntity.ok(userFavoriteService.getFavorites(usersIdx));
        } catch (Exception e) {
            log.error("사용자 즐겨찾기 목록 읽는 중 오류", e);
            return ResponseEntity.status(500).body(Map.of("error", "사용자 즐겨찾기 목록 읽는 중 오류"));
        }
    }

    // 즐겨찾기 삭제
    @DeleteMapping("/{favoriteId}")
    public ResponseEntity<String> removeFavorite(@PathVariable("favoriteId") int favoriteId) {
        boolean success = userFavoriteService.deleteFavorite(favoriteId);
        return success ? ResponseEntity.ok("즐겨찾기 삭제 완료") :
                ResponseEntity.badRequest().body("삭제 실패");
    }

}
