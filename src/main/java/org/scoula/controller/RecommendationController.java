package org.scoula.controller;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.scoula.dto.HouseListDTO;
import org.scoula.mapper.UserMapper;
import org.scoula.security.util.JwtProcessor;
import org.scoula.service.RecommendationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
//@RequestMapping("/recomemndation")
public class RecommendationController {
    private final RecommendationService recommendationService;
    private final JwtProcessor jwtProcessor;
    private final UserMapper userMapper;

    @GetMapping("/recommendation")
    @ResponseBody
    public List<HouseListDTO> recommendation(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", ""); // "Bearer " 제거
        String username = jwtProcessor.getUsername(token);
        int usersIdx = userMapper.findUserIdxByUserId(username);
        return recommendationService.getRecommendationList(usersIdx);
    }
}
