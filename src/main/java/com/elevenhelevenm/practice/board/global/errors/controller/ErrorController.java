package com.elevenhelevenm.practice.board.global.errors.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@RequestMapping("/errors")
@Controller
public class ErrorController {

    @GetMapping("/denied")
    public void page403() {}
}
