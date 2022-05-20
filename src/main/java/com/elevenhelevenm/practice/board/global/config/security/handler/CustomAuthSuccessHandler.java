package com.elevenhelevenm.practice.board.global.config.security.handler;

import com.elevenhelevenm.practice.board.global.config.security.auth.usernamepassword.PrincipalDetailsV2;
import com.elevenhelevenm.practice.board.domain.log.dto.LogDto;
import com.elevenhelevenm.practice.board.domain.user.model.Role;
import com.elevenhelevenm.practice.board.domain.user.model.UserV2;
import com.elevenhelevenm.practice.board.domain.log.service.LogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Component
public class CustomAuthSuccessHandler implements AuthenticationSuccessHandler {

    private final LogService logService;

    private final RequestCache requestCache = new HttpSessionRequestCache();

    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    private final String DEFAULT_URL = "/";

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        //로그 저장
        saveSuccessLog(request);

        //에러 세션 지우기
        clearAuthenticationAttributes(request);

        //리다이렉트 설정
        PrincipalDetailsV2 principal = (PrincipalDetailsV2) authentication.getPrincipal();
        UserV2 user = principal.getUser();

        if (user.getRole().equals(Role.ADMIN)) {
            redirectStrategy.sendRedirect(request, response, "/admin");
        } else {
            resultRedirectStrategy(request, response);
        }
    }

    private void saveSuccessLog(HttpServletRequest request) {
        String username = request.getParameter("username");
        String ip = request.getRemoteAddr();

        LogDto loginLogDto = LogDto.builder()
                .username(username)
                .ip(ip)
                .isSuccessLogin(true)
                .build();

        logService.saveLoginLog(loginLogDto);
    }

    protected void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }

    protected void resultRedirectStrategy(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String targetUrl = DEFAULT_URL;
        SavedRequest savedRequest = requestCache.getRequest(request, response);
        if (savedRequest != null) targetUrl = savedRequest.getRedirectUrl();

        redirectStrategy.sendRedirect(request, response, targetUrl);
    }
}
