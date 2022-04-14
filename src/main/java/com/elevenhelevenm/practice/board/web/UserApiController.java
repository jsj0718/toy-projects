package com.elevenhelevenm.practice.board.web;

import com.elevenhelevenm.practice.board.service.UserService;
import com.elevenhelevenm.practice.board.web.dto.request.user.UserSaveRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RequiredArgsConstructor
@RestController
public class UserApiController {

    private final UserService userService;

    @PostMapping("/api/v1/joinProc")
    public Long joinProc(@Valid @RequestBody UserSaveRequestDto requestDto) {
        // TODO: 2022-04-14 세션 존재하는 경우 Redirect 시키기
        
        return userService.join(requestDto);
    }



}
