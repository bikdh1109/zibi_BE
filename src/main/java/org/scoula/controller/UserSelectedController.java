package org.scoula.controller;

import lombok.RequiredArgsConstructor;
import org.scoula.dto.UserSelectedDTO;
import org.scoula.security.util.JwtProcessor;
import org.scoula.service.UserSelectedService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/v1/user/preferences")
@RequiredArgsConstructor
public class UserSelectedController {

    private final UserSelectedService userSelectedService;
    private final JwtProcessor jwtProcessor;

    private String extractUserIdFromToken(String token) {
        return jwtProcessor.getUsername(token.replace("Bearer ", ""));
    }

    @PostMapping
    public ResponseEntity<Map<String, String>>  saveUserSelected(@RequestHeader("Authorization") String token,
                                                                 @RequestBody UserSelectedDTO userSelectedDTO) {
        String userId = extractUserIdFromToken(token);
        userSelectedService.saveAllPreferences(userId, userSelectedDTO);
        Map<String, String> response = new HashMap<>();
        response.put("message", "사용자 선호 정보가 저장되었습니다.");

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public UserSelectedDTO getUserSelected(@RequestHeader("Authorization") String token) {
        String userId = extractUserIdFromToken(token);
        return userSelectedService.getUserSelected(userId);
    }
}
