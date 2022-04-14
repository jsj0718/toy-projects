package com.elevenhelevenm.practice.board.web;

import com.elevenhelevenm.practice.board.config.security.auth.LoginUser;
import com.elevenhelevenm.practice.board.config.security.dto.SessionUser;
import com.elevenhelevenm.practice.board.service.UserService;
import com.elevenhelevenm.practice.board.web.dto.request.user.UserSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;

    @GetMapping("/login")
    public String login (
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "exception", required = false) String exception,
            Model model,
            @LoginUser SessionUser user) {

        if (user != null) {
            return "redirect:/";
        }

        if (error != null && exception != null) {
            model.addAttribute("error", error);
            model.addAttribute("exception", exception);
        }

        return "user/login";
    }

    @GetMapping("/join")
    public String join() {
        return "user/join";
    }

    @PostMapping("/joinProc")
    public String joinProc(UserSaveRequestDto requestDto) {
        userService.join(requestDto);
        return "redirect:/login";
    }

}
