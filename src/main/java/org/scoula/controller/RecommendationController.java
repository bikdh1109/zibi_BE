package org.scoula.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.scoula.dto.HouseListDTO;
import org.scoula.dto.RecommendationListDTO;
import org.scoula.mapper.UserMapper;
import org.scoula.security.util.JwtProcessor;
import org.scoula.service.RecommendationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "청약 추천 API")
@RestController
@RequiredArgsConstructor
//@RequestMapping("/recomemndation")
public class RecommendationController {
    private final RecommendationService recommendationService;
    private final JwtProcessor jwtProcessor;
    private final UserMapper userMapper;

    @ApiOperation(value = "추천 청약 리스트 조회", notes = "선호 정보 기반 추천 청약 리스트를 반환합니다.")
    @GetMapping("/recommendation")
    @ResponseBody
    public ResponseEntity<List<RecommendationListDTO>> recommendation(@ApiParam(hidden = true) @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", ""); // "Bearer " 제거
        String username = jwtProcessor.getUsername(token);
        int usersIdx = userMapper.findUserIdxByUserId(username);
        return ResponseEntity.ok(recommendationService.getRecommendationList(usersIdx));
    }
}