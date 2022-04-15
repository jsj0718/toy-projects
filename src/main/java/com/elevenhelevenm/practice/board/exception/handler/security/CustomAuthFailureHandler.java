package com.elevenhelevenm.practice.board.exception.handler.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLEncoder;

@RequiredArgsConstructor
@Component
public class CustomAuthFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    private final HttpSession httpSession;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        httpSession.removeAttribute("user");

        String errorMsg;

        if (exception instanceof BadCredentialsException) {
            errorMsg = "아이디 또는 비밀번호가 맞지 않습니다. 다시 확인해 주세요.";
        } else {
            errorMsg = "알 수 없는 이유로 로그인에 실패하였습니다 관리자에게 문의하세요.";
        }

        errorMsg = URLEncoder.encode(errorMsg, "UTF-8");
        setDefaultFailureUrl("/login?error=true&exception=" + errorMsg);

        super.onAuthenticationFailure(request, response, exception);

    }
}
