package com.elevenhelevenm.practice.board.web;

import com.elevenhelevenm.practice.board.config.security.SecurityConfig;
import com.elevenhelevenm.practice.board.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = IndexController.class,
    excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)
})
class IndexControllerTest {

    @MockBean
    UserService userService;

    @Autowired
    MockMvc mvc;

}