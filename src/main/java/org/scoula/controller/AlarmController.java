//package org.scoula.controller;
//
//import com.google.firebase.messaging.FirebaseMessagingException;
//import io.swagger.annotations.*;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.log4j.Log4j2;
//import org.scoula.dto.AlarmRequestDTO;
//import org.scoula.dto.AlramMessageRequest;
//import org.scoula.mapper.UserMapper;
//import org.scoula.security.util.JwtProcessor;
//import org.scoula.service.AlarmService;
//import org.scoula.service.FcmService;
//import org.scoula.util.TokenUtils;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.Map;
//
//@RestController
//@RequestMapping("/v1/api")
//@Log4j2
//@RequiredArgsConstructor
//@Api(tags = "알림/FCM")
//public class AlarmController {
//    private final AlarmService alarmService;
//    private final TokenUtils tokenUtils;
//    private final JwtProcessor jwtProcessor;
//    private final UserMapper userMapper;
//    private final FcmService fcmService;
//
//    @PutMapping("/token")
//    @ApiOperation(value = "FCM 토큰 저장/갱신", notes = "Authorization: Bearer {accessToken} 필요")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "Authorization", value = "Bearer 액세스 토큰",
//                    required = true, paramType = "header", dataType = "string",
//                    example = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
//    })
//
//    public ResponseEntity<?> saveToken(
//            @ApiParam(hidden = true) @RequestHeader("Authorization") String bearerToken,
//            @RequestBody AlarmRequestDTO body) {
//        try {
//            if (body == null || body.getFcmToken() == null || body.getFcmToken().isBlank()) {
//                return ResponseEntity.badRequest().body(Map.of("error", "fcmToken이 비어있습니다."));
//            }
//
//            String accessToken = tokenUtils.extractAccessToken(bearerToken);
//            String userId = jwtProcessor.getUsername(accessToken);
//            int userIdx = userMapper.findUserIdxByUserId(userId);
//
//            if (userIdx <= 0) {
//                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
//                        .body(Map.of("error", "유효하지 않은 사용자입니다."));
//            }
//
//            alarmService.saveFcmToken(userIdx, body.getFcmToken());
//            log.info("Save Token - userIdx={}, token={}", userIdx, body.getFcmToken());
//
//            return ResponseEntity.ok(Map.of("message", "FCM 토큰 저장 완료"));
//        } catch (Exception e) {
//            log.error("FCM 토큰 저장 중 오류 발생", e);
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(Map.of("error", "서버 내부 오류"));
//        }
//    }
//
//    @DeleteMapping("/token")
//    @ApiOperation(value = "FCM 토큰 삭제(초기화)", notes = "Authorization: Bearer {accessToken} 필요")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "Authorization", value = "Bearer 액세스 토큰",
//                    required = true, paramType = "header", dataType = "string",
//                    example = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
//    })
//
//    public ResponseEntity<?> deleteToken(@ApiParam(hidden = true) @RequestHeader("Authorization") String bearerToken) {
//        try {
//            if (bearerToken == null || bearerToken.isBlank()) {
//                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
//                        .body(Map.of("error", "Authorization 헤더가 없습니다."));
//            }
//
//            String accessToken = tokenUtils.extractAccessToken(bearerToken); // "Bearer xxx" → "xxx"
//            if (accessToken == null || accessToken.isBlank()) {
//                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
//                        .body(Map.of("error", "액세스 토큰이 유효하지 않습니다."));
//            }
//
//            String userId = jwtProcessor.getUsername(accessToken);
//            if (userId == null || userId.isBlank()) {
//                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
//                        .body(Map.of("error", "토큰에서 사용자 정보를 추출하지 못했습니다."));
//            }
//
//            Integer userIdx = userMapper.findUserIdxByUserId(userId);
//            if (userIdx == null || userIdx <= 0) {
//                return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                        .body(Map.of("error", "사용자를 찾을 수 없습니다."));
//            }
//
//            int updated = userMapper.initFcmTokenByIdx(userIdx);
//
//            if (updated == 0) {
//                return ResponseEntity.ok(Map.of("message", "이미 FCM 토큰이 비어있습니다."));
//            }
//
//            return ResponseEntity.ok(Map.of("message","FCM토큰 초기화 완료"));
//
//        } catch (Exception e) {
//            log.error("FCM 토큰 삭제 중 오류", e);
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(Map.of("error", "서버 내부 오류"));
//        }
//    }
//
//    @PostMapping("/send")
//    public ResponseEntity<?> sendPush(
//            @RequestHeader("Authorization") String bearerToken,
//            @RequestBody AlramMessageRequest alramMessageRequest) {
//
//        try {
//            // 1. Authorization 헤더 검증
//            if (bearerToken == null || !bearerToken.startsWith("Bearer ")) {
//                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
//                        .body(Map.of("error", "유효하지 않은 Authorization 헤더입니다."));
//            }
//
//            // 2. Access Token 추출 및 유효성 검증
//            String accessToken = tokenUtils.extractAccessToken(bearerToken);
//            if (accessToken == null || accessToken.isBlank()) {
//                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
//                        .body(Map.of("error", "액세스 토큰이 없습니다."));
//            }
//
//            // 3. 사용자 식별
//            String userId = jwtProcessor.getUsername(accessToken);
//            if (userId == null || userId.isBlank()) {
//                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
//                        .body(Map.of("error", "토큰에서 사용자 정보를 확인할 수 없습니다."));
//            }
//
//            // 4. userIdx 조회
//            Integer userIdx = userMapper.findUserIdxByUserId(userId);
//            if (userIdx == null) {
//                return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                        .body(Map.of("error", "사용자 정보를 찾을 수 없습니다."));
//            }
//
//            // 5. 요청 메시지 유효성 검사
//            if (alramMessageRequest == null ||
//                    alramMessageRequest.getTitle() == null || alramMessageRequest.getTitle().isBlank() ||
//                    alramMessageRequest.getBody() == null || alramMessageRequest.getBody().isBlank()) {
//                return ResponseEntity.badRequest()
//                        .body(Map.of("error", "title과 body는 필수 입력값입니다."));
//            }
//
//            // 6. FCM 토큰 조회
//            String token = userMapper.findFcmTokenByUserIdx(userIdx);
//            if (token == null || token.isBlank()) {
//                return ResponseEntity.badRequest()
//                        .body(Map.of("error", "등록된 FCM 토큰이 없습니다."));
//            }
//
//            // 7. 푸시 전송
//            fcmService.sendMessage(token, alramMessageRequest.getTitle(), alramMessageRequest.getBody());
//
//            return ResponseEntity.ok(Map.of("message", "푸시 전송 완료"));
//
//        } catch (FirebaseMessagingException e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(Map.of("error", "푸시 전송 실패", "details", e.getMessage()));
//
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(Map.of("error", "서버 오류", "details", e.getMessage()));
//        }
//    }
//
//
//
//}
