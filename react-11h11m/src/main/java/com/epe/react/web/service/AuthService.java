package com.epe.react.web.service;

import com.epe.react.config.jwt.TokenProvider;
import com.epe.react.domain.Member;
import com.epe.react.domain.MemberRepository;
import com.epe.react.web.dto.MemberRequestDto;
import com.epe.react.web.dto.MemberResponseDto;
import com.epe.react.web.dto.TokenDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class AuthService {
    private final MemberRepository memberRepository;
    private final AuthenticationManagerBuilder managerBuilder;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    public MemberResponseDto signup(MemberRequestDto requestDto) {
        if (memberRepository.existsByEmail(requestDto.getEmail()))
            throw new IllegalArgumentException("이미 존재하는 유저 이메일 입니다.");

        Member member = requestDto.toMember(passwordEncoder);
        return new MemberResponseDto(memberRepository.save(member));
    }

    public TokenDto login(MemberRequestDto requestDto) {
        UsernamePasswordAuthenticationToken authenticationToken = requestDto.toAuthentication();

        Authentication authentication = managerBuilder.getObject().authenticate(authenticationToken);

        return tokenProvider.generateTokenDto(authentication);
    }
}
