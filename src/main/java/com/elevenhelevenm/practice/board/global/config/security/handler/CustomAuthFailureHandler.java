package com.elevenhelevenm.practice.board.global.config.security.handler;

import com.elevenhelevenm.practice.board.domain.log.dto.LogDto;
import com.elevenhelevenm.practice.board.domain.log.service.LogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

@Slf4j
@RequiredArgsConstructor
@Component
public class CustomAuthFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    private final LogService logService;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        //로그 저장
        saveFailLog(request, exception);

        //에러 메세지 설정 (인코딩 포함)
        String errorMsg;

        if (exception instanceof BadCredentialsException) {
            errorMsg = "아이디 또는 비밀번호가 맞지 않습니다. 다시 확인해주세요.";
        } else if (exception instanceof InternalAuthenticationServiceException) {
            errorMsg = "권한이 적절하지 않습니다. 다시 시도해주세요.";
        } else {
            errorMsg = "알 수 없는 이유로 로그인에 실패하였습니다 관리자에게 문의하세요.";
        }

        errorMsg = URLEncoder.encode(errorMsg, "UTF-8");

        String defaultUrl = "/login?error=true&exception=" + errorMsg;
        setDefaultFailureUrl(defaultUrl);

        super.onAuthenticationFailure(request, response, exception);
    }

    private void saveFailLog(HttpServletRequest request, AuthenticationException exception) {
        String username = request.getParameter("username");
        String ip = request.getRemoteAddr();

        LogDto loginLogDto = LogDto.builder()
                .username(username)
                .ip(ip)
                .isSuccessLogin(false)
                .exception(exception.toString())
                .build();

        logService.saveLoginLog(loginLogDto);
    }
}
