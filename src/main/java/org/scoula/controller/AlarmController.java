package org.scoula.controller;



import com.google.firebase.messaging.FirebaseMessagingException;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.scoula.dto.swagger.Alarm.SwaggerChungyakAlarmDTO;
import org.scoula.dto.swagger.Alarm.SwaggerDepositDTO;
import org.scoula.mapper.UserMapper;
import org.scoula.security.util.JwtProcessor;
import org.scoula.service.AlarmService;
import org.scoula.util.TokenUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/newnotice")
    public ResponseEntity<?> createNewNotice(@RequestHeader("Authorization") String bearerToken,
    @RequestBody SwaggerChungyakAlarmDTO body) {
        String accessToken = tokenUtils.extractAccessToken(bearerToken);
        String userId = jwtProcessor.getUsername(accessToken);
        int userIdx = userMapper.findUserIdxByUserId(userId);
        alarmService.createNewNotice(body.getTitle(), body.getContent(),body.getLink(),userIdx);
        return ResponseEntity.ok(Map.of("message", "알람 저장 완료"));
    }

    /** 청약 접수 시작 */
    @PostMapping("/application-start")
    public ResponseEntity<?> createApplicationStart(
            @RequestHeader("Authorization") String bearerToken,
            @RequestBody SwaggerChungyakAlarmDTO body) {
        String accessToken = tokenUtils.extractAccessToken(bearerToken);
        String userId = jwtProcessor.getUsername(accessToken);
        int userIdx = userMapper.findUserIdxByUserId(userId);
        alarmService.createApplicationStart(body.getTitle(), body.getContent(),body.getLink(),userIdx);
        return ResponseEntity.ok(Map.of("message", "알람 저장 완료"));
    }

    @PostMapping("/create-depositunpaid")
    public ResponseEntity<?> createDepositUnpaid(
            @RequestHeader("Authorization") String bearerToken,
            @RequestBody SwaggerDepositDTO body) {
        String accessToken = tokenUtils.extractAccessToken(bearerToken);
        String userId = jwtProcessor.getUsername(accessToken);
        int userIdx = userMapper.findUserIdxByUserId(userId);
        alarmService.createDepositUnpaid(body.getTitle(), body.getContent(),userIdx);
        return ResponseEntity.ok(Map.of("message", "알람 저장 완료"));
    }


}
