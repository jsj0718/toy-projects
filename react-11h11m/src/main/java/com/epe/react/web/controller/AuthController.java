package com.epe.react.web.controller;

import com.epe.react.domain.Member;
import com.epe.react.web.dto.MemberRequestDto;
import com.epe.react.web.dto.TokenDto;
import com.epe.react.web.service.AuthService;
import com.epe.react.web.dto.MemberResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<MemberResponseDto> findMemberInfoById(@RequestBody MemberRequestDto requestDto) {
        return ResponseEntity.ok(authService.signup(requestDto));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDto> findMemberInfoByEmail(@RequestBody MemberRequestDto requestDto) {
        return ResponseEntity.ok(authService.login(requestDto));
    }
}
