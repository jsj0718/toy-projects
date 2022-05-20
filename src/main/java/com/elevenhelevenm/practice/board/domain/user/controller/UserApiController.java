package com.elevenhelevenm.practice.board.domain.user.controller;

import com.elevenhelevenm.practice.board.domain.user.dto.request.UserSaveRequestDto;
import com.elevenhelevenm.practice.board.domain.user.dto.request.UserV2SaveRequestDto;
import com.elevenhelevenm.practice.board.domain.user.service.UserService;
import com.elevenhelevenm.practice.board.global.aop.RedirectHome;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class UserApiController {

    private final UserService userService;

    @RedirectHome
    @PostMapping("/v1/joinProc")
    public Long joinProc(@Valid @RequestBody UserSaveRequestDto requestDto) {
        return userService.join(requestDto);
    }

    @DeleteMapping("/v1/user/{id}")
    public Long deleteUser(@PathVariable Long id) {
        userService.delete(id);
        return id;
    }

    @RedirectHome
    @PostMapping("/v2/joinProc")
    public Long joinProcV2(@Valid @RequestBody UserV2SaveRequestDto requestDto) {
        return userService.joinV2(requestDto);
    }

}
