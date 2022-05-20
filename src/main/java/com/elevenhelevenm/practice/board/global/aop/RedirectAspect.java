package com.elevenhelevenm.practice.board.global.aop;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Aspect
@Component
public class RedirectAspect {

    private final HttpServletResponse response;

    @Before("@annotation(com.elevenhelevenm.practice.board.global.aop.RedirectHome)")
    public void redirectHome(JoinPoint joinPoint) {
        if (isLogin()) {
            try {
                response.sendRedirect("redirect:/");
            } catch (IOException e) {
                log.info("Redirect 실패 : {}", e.getMessage());
            }
        }
    }

    private boolean isLogin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || AnonymousAuthenticationToken.class.isAssignableFrom(authentication.getClass())) {
            return false;
        }
        return true;
    }

}
