package org.scoula.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.scoula.dto.AccountConnectDTO;
import org.scoula.dto.ChungyakAccountDTO;
import org.scoula.service.CodefApiService;
import org.scoula.security.util.JwtProcessor;
import org.scoula.mapper.UserMapper;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1/account")
@Log4j2
@RequiredArgsConstructor
public class AccountController {

    private final CodefApiService codefApiService;
    private final JwtProcessor jwtProcessor;
    private final UserMapper userMapper;

    @PostMapping("/connect")
    public Map<String, String> autoConnectAndFetchAccounts(
            @RequestHeader("Authorization") String token,
            @RequestBody AccountConnectDTO requestDto
    ) throws Exception {
        String userId = jwtProcessor.getUsername(token.replace("Bearer ", ""));
        int userIdx = userMapper.findUserIdxByUserId(userId);

        codefApiService.autoConnectAndFetchChungyakAccounts(
                requestDto.getId(),
                requestDto.getPassword(),
                requestDto.getOrganization(),
                requestDto.getBankName(),
                userIdx
        );

        Map<String, String> response = new HashMap<>();
        response.put("message", "계좌 연동이 완료되었습니다.");
        return response;
    }

    @GetMapping("")
    public List<ChungyakAccountDTO> getUserAccounts(@RequestHeader("Authorization") String token) {
        String userId = jwtProcessor.getUsername(token.replace("Bearer ", ""));
        int userIdx = userMapper.findUserIdxByUserId(userId);

        return codefApiService.getChungyakAccountsByUserIdx(userIdx);
    }
}
