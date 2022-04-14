package com.elevenhelevenm.practice.board.web;

import com.elevenhelevenm.practice.board.service.UserService;
import com.elevenhelevenm.practice.board.web.dto.request.user.UserSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UserApiController {

    private final UserService userService;

    @PostMapping("/joinProc")
    public String joinProc(UserSaveRequestDto requestDto) {
        userService.join(requestDto);
        return "redirect:/login";
    }

}
