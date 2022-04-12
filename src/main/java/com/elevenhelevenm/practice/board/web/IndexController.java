package com.elevenhelevenm.practice.board.web;

import com.elevenhelevenm.practice.board.service.UserService;
import com.elevenhelevenm.practice.board.web.dto.request.UserSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final UserService userService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/login")
    public String login (
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "exception", required = false) String exception,
            Model model,
            Authentication authentication) {

        if (authentication != null) {
            return "redirect:/";
        }

        if (error != null && exception != null) {
            model.addAttribute("error", error);
            model.addAttribute("exception", exception);
        }

        return "login";
    }

    @GetMapping("/join")
    public String join() {
        return "join";
    }

    @PostMapping("/joinProc")
    public String joinProc(UserSaveRequestDto requestDto) {
        userService.join(requestDto);
        return "redirect:/";
    }

}