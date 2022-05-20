package com.elevenhelevenm.practice.board.global.aop;

import com.elevenhelevenm.practice.board.domain.log.dto.LogDto;
import com.elevenhelevenm.practice.board.domain.log.service.LogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RequiredArgsConstructor
@Aspect
@Component
public class LogAspect {

    private final LogService logService;

    private final HttpServletRequest request;


    @Before("@annotation(com.elevenhelevenm.practice.board.global.aop.PageLogTrace)")
    public void logging() {
        String username = (isLogin()) ? getLoginUsername() : "GUEST";
        String uri = request.getRequestURI();
        String ip = request.getRemoteAddr();

        LogDto pageLogDto = LogDto.builder()
                .username(username)
                .url(uri)
                .ip(ip)
                .build();

        logService.savePageLog(pageLogDto);
    }

    private boolean isLogin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || AnonymousAuthenticationToken.class.isAssignableFrom(authentication.getClass())) {
            return false;
        }
        return true;
    }

    private String getLoginUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
