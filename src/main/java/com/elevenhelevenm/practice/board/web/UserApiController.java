package com.elevenhelevenm.practice.board.web;

import com.elevenhelevenm.practice.board.config.security.auth.LoginUser;
import com.elevenhelevenm.practice.board.config.security.dto.SessionUser;
import com.elevenhelevenm.practice.board.service.UserService;
import com.elevenhelevenm.practice.board.web.dto.request.user.UserSaveRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@RestController
public class UserApiController {

    private final UserService userService;

    @PostMapping("/api/v1/joinProc")
    public Long joinProc(@Valid @RequestBody UserSaveRequestDto requestDto,
                         HttpServletResponse response,
                         @LoginUser SessionUser user) {
        if (user != null) {
            try {
                response.sendRedirect("redirect:/");
            } catch (IOException e) {
                log.info("Redirect 실패 : {}", e.getMessage());
            }
        }

        return userService.join(requestDto);
    }

}
