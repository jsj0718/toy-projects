package com.elevenhelevenm.practice.board.domain.user.controller;

import com.elevenhelevenm.practice.board.domain.user.dto.response.UserResponseDto;
import com.elevenhelevenm.practice.board.domain.user.service.UserService;
import com.elevenhelevenm.practice.board.global.aop.PageLogTrace;
import com.elevenhelevenm.practice.board.global.aop.RedirectHome;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;

    @RedirectHome
    @PageLogTrace
    @GetMapping("/login")
    public ModelAndView login(
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "exception", required = false) String exception,
            ModelAndView mav) {

        if (error != null && exception != null) {
            mav.addObject("error", error);
            mav.addObject("exception", exception);
        }

        mav.setViewName("user/login");

        return mav;
    }

    @RedirectHome
    @PageLogTrace
    @GetMapping("/super-admin-login")
    public ModelAndView superAdminLogin(
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "exception", required = false) String exception,
            ModelAndView mav) {

        if (error != null && exception != null) {
            mav.addObject("error", error);
            mav.addObject("exception", exception);
        }

        mav.setViewName("user/super-admin-login");

        return mav;
    }

    @RedirectHome
    @PageLogTrace
    @GetMapping("/join")
    public String join() {
        return "user/join";
    }

    @PageLogTrace
    @GetMapping("/member")
    public String member() {
        return "user/member/home";
    }

    @PageLogTrace
    @GetMapping("/manager")
    public String manager() {
        return "user/manager/home";
    }

    @PageLogTrace
    @GetMapping("/admin")
    public ModelAndView admin(ModelAndView mav) {
        List<UserResponseDto> users = userService.findAll();
        mav.addObject("users", users);
        mav.setViewName("/user/admin/home");

        return mav;
    }
}
