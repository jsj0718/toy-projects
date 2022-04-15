package com.elevenhelevenm.practice.board.web;

import com.elevenhelevenm.practice.board.config.security.auth.LoginUser;
import com.elevenhelevenm.practice.board.config.security.dto.SessionUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
public class UserController {

    @GetMapping("/login")
    public String login (
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "exception", required = false) String exception,
            @LoginUser SessionUser user,
            Model model) {

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
    public String join(@LoginUser SessionUser user) {

        if (user != null) {
            return "redirect:/";
        }

        return "user/join";
    }

}
