package com.elevenhelevenm.practice.board.web;

import com.elevenhelevenm.practice.board.domain.user.User;
import com.elevenhelevenm.practice.board.repository.UserRepository;
import com.elevenhelevenm.practice.board.service.UserService;
import com.elevenhelevenm.practice.board.web.dto.request.user.UserSaveRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserApiControllerTest {

    @LocalServerPort
    int port;

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    WebApplicationContext context;

    MockMvc mvc;

    @BeforeEach
    void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @AfterEach
    void cleanup() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("회원가입을 한다.")
    void joinProc() throws Exception {
        //given
        String username = "username";
        String password = "password";
        String email = "test@test.com";

        UserSaveRequestDto requestDto = UserSaveRequestDto.builder()
                .username(username)
                .password(password)
                .email(email)
                .build();

        String url = "http://localhost:" + port + "/api/v1/joinProc";

        //when
        MvcResult result = mvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andReturn();

        long savedId = Long.parseLong(result.getResponse().getContentAsString());

        //then
        Optional<User> user = userRepository.findById(savedId);

        assertThat(user).isNotEmpty();
        assertThat(user.get().getUsername()).isEqualTo(username);
        assertThat(user.get().getEmail()).isEqualTo(email);
    }

    @Test
    @DisplayName("회원가입 시 아이디, 비밀번호, 이메일이 빈 값이거나 Null이면 오류가 발생한다.")
    void joinProcExceptionByBlank() throws Exception {
        //세 가지 중 이메일 제외
        String username = "username";
        String password = "password";

        UserSaveRequestDto requestDto = UserSaveRequestDto.builder()
                .username(username)
                .password(password)
                .build();

        String url = "http://localhost:" + port + "/api/v1/joinProc";

        mvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isBadRequest());
    }
}