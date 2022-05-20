package com.elevenhelevenm.practice.board.config.security;

import com.elevenhelevenm.practice.board.domain.user.dto.request.UserV2SaveRequestDto;
import com.elevenhelevenm.practice.board.domain.user.model.Role;
import com.elevenhelevenm.practice.board.global.config.security.auth.usernamepassword.PrincipalDetailsV2;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.util.List;

public class WithAuthUserSecurityContextFactory implements WithSecurityContextFactory<WithAuthUser> {
    @Override
    public SecurityContext createSecurityContext(WithAuthUser annotation) {
        String username = annotation.username();
        String password = annotation.password();
        String email = annotation.email();
        Role role = annotation.role();

        UserV2SaveRequestDto requestDto = UserV2SaveRequestDto.builder()
                .username(username)
                .password(password)
                .email(email)
                .role(role)
                .build();

        PrincipalDetailsV2 auth = null;
        if (role.equals(Role.MEMBER)) auth = new PrincipalDetailsV2(requestDto.toMember());
        else if (role.equals(Role.MANAGER)) auth = new PrincipalDetailsV2(requestDto.toManager());
        else if (role.equals(Role.ADMIN)) auth = new PrincipalDetailsV2(requestDto.toAdmin());

        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(auth, password, List.of(new SimpleGrantedAuthority(role.getKey())));
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(token);
        return context;
    }
}
