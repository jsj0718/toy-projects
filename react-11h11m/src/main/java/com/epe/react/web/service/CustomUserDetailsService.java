package com.epe.react.web.service;

import com.epe.react.domain.Member;
import com.epe.react.domain.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return memberRepository.findByEmail(username)
                .map(this::createUserDetails)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("{}을 찾을 수 없습니다.", username)));
    }

    private UserDetails createUserDetails(Member member) {
        SimpleGrantedAuthority grantedAuthority = new SimpleGrantedAuthority(member.getAuthority().name());
        return new User(String.valueOf(member.getId()), member.getPassword(), Collections.singleton(grantedAuthority));
    }

}
