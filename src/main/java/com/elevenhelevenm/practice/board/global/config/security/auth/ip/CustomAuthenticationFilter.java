package com.elevenhelevenm.practice.board.global.config.security.auth.ip;

import com.elevenhelevenm.practice.board.domain.user.model.Role;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

@Slf4j
public class CustomAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    public CustomAuthenticationFilter(RequestMatcher requiresAuthenticationRequestMatcher) {
        super(requiresAuthenticationRequestMatcher);
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        String ip = request.getRemoteAddr();

        log.info("ip : {}", ip);

        Collection<GrantedAuthority> collection = new ArrayList<>();
        collection.add(() -> Role.SUPER_ADMIN.getKey());

        return getAuthenticationManager().authenticate(new CustomAuthenticationToken(ip, collection));
    }
}
