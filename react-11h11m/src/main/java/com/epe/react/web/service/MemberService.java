package com.epe.react.web.service;

import com.epe.react.config.jwt.SecurityUtil;
import com.epe.react.domain.Member;
import com.epe.react.domain.MemberRepository;
import com.epe.react.web.dto.MemberResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberResponseDto getMyInfoBySecurity() {
        System.out.println("SecurityUtil.getCurrentMemberId() = " + SecurityUtil.getCurrentMemberId());
        return memberRepository.findById(SecurityUtil.getCurrentMemberId())
                .map(MemberResponseDto::new)
                .orElseThrow(() -> new IllegalArgumentException("로그인 유저 정보가 없습니다."));
    }

    @Transactional
    public MemberResponseDto changeMemberNickname(String email, String nickname) {
        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("유저 정보가 없습니다."));
        member.setNickname(nickname);
        return new MemberResponseDto(memberRepository.save(member));
    }

    @Transactional
    public MemberResponseDto changeMemberPassword(String exPassword, String newPassword) {
        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId()).orElseThrow(() -> new IllegalArgumentException("로그인 유저 정보가 없습니다"));
        if (!passwordEncoder.matches(exPassword, member.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 맞지 않습니다");
        }
        member.setPassword(passwordEncoder.encode((newPassword)));
        return new MemberResponseDto(memberRepository.save(member));
    }
}
