package org.scoula.controller;

import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.checkerframework.checker.units.qual.A;
import org.scoula.dto.AlarmDetailDTO;
import org.scoula.dto.AlarmListDTO;
import org.scoula.dto.swagger.Alarm.SwaggerChungyakAlarmDTO;
import org.scoula.dto.swagger.Alarm.SwaggerDepositDTO;
import org.scoula.mapper.UserMapper;
import org.scoula.security.util.JwtProcessor;
import org.scoula.service.AlarmService;
import org.scoula.util.TokenUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1/alarm")
@Log4j2
@RequiredArgsConstructor
@Api(tags = "알림/FCM")
public class AlarmController {

    private final AlarmService alarmService;
    private final TokenUtils tokenUtils;
    private final JwtProcessor jwtProcessor;
    private final UserMapper userMapper;

    /* ========================= 공통 ========================= */

    private int currentUserIdx(String bearerToken) {
        String accessToken = tokenUtils.extractAccessToken(bearerToken);
        String userId = jwtProcessor.getUsername(accessToken);
        return userMapper.findUserIdxByUserId(userId);
    }

    /* ========================= 생성 ========================= */

    @PostMapping("/newnotice")
    @ApiOperation(value = "새 청약 공고 알람 생성")
    @ApiImplicitParam(name = "Authorization", value = "Bearer 액세스 토큰", required = true, paramType = "header")
    public ResponseEntity<?> createNewNotice(
            @ApiParam(hidden = true) @RequestHeader("Authorization") String bearerToken,
            @RequestBody SwaggerChungyakAlarmDTO body) {

        int userIdx = currentUserIdx(bearerToken);
        Long alarmIdx = alarmService.createNewNotice(body.getTitle(), body.getContent(), body.getLink(), userIdx);
        return ResponseEntity.ok(Map.of("message", "알람 저장 완료", "alarmIdx", alarmIdx));
    }

    /** 청약 접수 시작 */
    @PostMapping("/application-start")
    @ApiOperation(value = "청약 접수 시작 알람 생성")
    @ApiImplicitParam(name = "Authorization", value = "Bearer 액세스 토큰", required = true, paramType = "header")
    public ResponseEntity<?> createApplicationStart(
            @ApiParam(hidden = true) @RequestHeader("Authorization") String bearerToken,
            @RequestBody SwaggerChungyakAlarmDTO body) {

        int userIdx = currentUserIdx(bearerToken);
        Long alarmIdx = alarmService.createApplicationStart(body.getTitle(), body.getContent(), body.getLink(), userIdx);
        return ResponseEntity.ok(Map.of("message", "알람 저장 완료", "alarmIdx", alarmIdx));
    }

    @PostMapping("/create-depositunpaid")
    @ApiOperation(value = "예치금 미납 알람 생성")
    @ApiImplicitParam(name = "Authorization", value = "Bearer 액세스 토큰", required = true, paramType = "header")
    public ResponseEntity<?> createDepositUnpaid(
            @ApiParam(hidden = true) @RequestHeader("Authorization") String bearerToken,
            @RequestBody SwaggerDepositDTO body) {

        int userIdx = currentUserIdx(bearerToken);
        Long alarmIdx = alarmService.createDepositUnpaid(body.getTitle(), body.getContent(), userIdx);
        return ResponseEntity.ok(Map.of("message", "알람 저장 완료", "alarmIdx", alarmIdx));
    }

    /* ========================= 조회 ========================= */

    @GetMapping("/list")
    @ApiOperation(value = "알람 목록 조회", notes = "onlyUnread=true면 미읽음만 반환")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer 액세스 토큰", required = true, paramType = "header"),
            @ApiImplicitParam(name = "onlyUnread", value = "미읽음만 조회 여부", required = false, paramType = "query", example = "false")
    })
    public ResponseEntity<List<AlarmListDTO>> getAlarmList(
            @ApiParam(hidden = true) @RequestHeader("Authorization") String bearerToken,
            @RequestParam(name = "onlyUnread", defaultValue = "false") boolean onlyUnread) {

        int userIdx = currentUserIdx(bearerToken);
        return ResponseEntity.ok(alarmService.getAlarmList(userIdx, onlyUnread));
    }

    @GetMapping("/{alarmIdx}")
    @ApiOperation(value = "알람 단건 상세 조회")
    @ApiImplicitParam(name = "Authorization", value = "Bearer 액세스 토큰", required = true, paramType = "header")
    public ResponseEntity<AlarmDetailDTO> getAlarmDetail(
            @ApiParam(hidden = true) @RequestHeader("Authorization") String bearerToken,
            @PathVariable("alarmIdx") Long alarmIdx) {

        int userIdx = currentUserIdx(bearerToken);
        AlarmDetailDTO detail = alarmService.getAlarmDetail(alarmIdx, userIdx);
        if (detail == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(detail);
    }

    /* ========================= 읽음 처리 ========================= */

    @PatchMapping("/{alarmIdx}/read")
    @ApiOperation(value = "알람 단건 읽음 처리")
    @ApiImplicitParam(name = "Authorization", value = "Bearer 액세스 토큰", required = true, paramType = "header")
    public ResponseEntity<?> markRead(
            @ApiParam(hidden = true) @RequestHeader("Authorization") String bearerToken,
            @PathVariable("alarmIdx") Long alarmIdx) {

        int userIdx = currentUserIdx(bearerToken);
        boolean updated = alarmService.markRead(alarmIdx, userIdx);
        if (!updated) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(Map.of("message", "읽음 처리 완료", "alarmIdx", alarmIdx));
    }

    @PatchMapping("/read-all")
    @ApiOperation(value = "알람 전체 읽음 처리")
    @ApiImplicitParam(name = "Authorization", value = "Bearer 액세스 토큰", required = true, paramType = "header")
    public ResponseEntity<?> markAllRead(
            @ApiParam(hidden = true) @RequestHeader("Authorization") String bearerToken) {

        int userIdx = currentUserIdx(bearerToken);
        int count = alarmService.markAllRead(userIdx);
        return ResponseEntity.ok(Map.of("message", "전체 읽음 처리 완료", "updatedCount", count));
    }

    /* ========================= 삭제 ========================= */

    @DeleteMapping("/{alarmIdx}")
    @ApiOperation(value = "알람 단건 삭제")
    @ApiImplicitParam(name = "Authorization", value = "Bearer 액세스 토큰", required = true, paramType = "header")
    public ResponseEntity<?> delete(
            @ApiParam(hidden = true) @RequestHeader("Authorization") String bearerToken,
            @PathVariable("alarmIdx") Long alarmIdx) {

        int userIdx = currentUserIdx(bearerToken);
        boolean deleted = alarmService.delete(alarmIdx, userIdx);
        if (!deleted) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(Map.of("message", "삭제 완료", "alarmIdx", alarmIdx));
    }

    @DeleteMapping("/all")
    @ApiOperation(value = "사용자 알람 전체 삭제")
    @ApiImplicitParam(name = "Authorization", value = "Bearer 액세스 토큰", required = true, paramType = "header")
    public ResponseEntity<?> deleteAllByUser(
            @ApiParam(hidden = true) @RequestHeader("Authorization") String bearerToken) {

        int userIdx = currentUserIdx(bearerToken);
        int count = alarmService.deleteAllByUser(userIdx);
        return ResponseEntity.ok(Map.of("message", "전체 삭제 완료", "deletedCount", count));
    }
}
