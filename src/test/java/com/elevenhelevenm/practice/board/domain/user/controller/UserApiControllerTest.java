package com.elevenhelevenm.practice.board.domain.user.controller;

import com.elevenhelevenm.practice.board.config.security.WithAuthUser;
import com.elevenhelevenm.practice.board.domain.user.dto.request.UserV2SaveRequestDto;
import com.elevenhelevenm.practice.board.domain.user.model.Role;
import com.elevenhelevenm.practice.board.domain.user.model.UserV2;
import com.elevenhelevenm.practice.board.domain.user.repository.UserRepositoryV2;
import com.elevenhelevenm.practice.board.domain.user.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserApiControllerTest {

    @LocalServerPort
    int port;

    @Autowired
    MockMvc mvc;

    @Autowired
    UserRepositoryV2<UserV2> userRepository;

    @Autowired
    UserService userService;

    @AfterEach
    void cleanup() {
        userRepository.deleteAll();
    }

    /**
     * 각 권한별 회원가입
     */
    @Test
    @DisplayName("일반 유저가 회원가입을 한다.")
    void joinMemberProc() throws Exception {
        //given
        String username = "username";
        String password = "password";
        String email = "test@test.com";
        Role role = Role.MEMBER;

        UserV2SaveRequestDto requestDto = UserV2SaveRequestDto.builder()
                .username(username)
                .password(password)
                .email(email)
                .role(role)
                .build();

        String url = "http://localhost:" + port + "/api/v2/joinProc";

        //when
        MvcResult result = mvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(new ObjectMapper().writeValueAsString(requestDto))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andReturn();

        long savedId = Long.parseLong(result.getResponse().getContentAsString());

        //then
        Optional<UserV2> member = userRepository.findById(savedId);

        assertThat(member).isNotEmpty();
        assertThat(member.get().getUsername()).isEqualTo(username);
        assertThat(member.get().getEmail()).isEqualTo(email);
        assertThat(member.get().getRole()).isEqualTo(role);
    }

    @Test
    @DisplayName("매니저 유저가 회원가입을 한다.")
    void joinManagerProc() throws Exception {
        //given
        String username = "username";
        String password = "password";
        String email = "test@test.com";
        Role role = Role.MANAGER;

        UserV2SaveRequestDto requestDto = UserV2SaveRequestDto.builder()
                .username(username)
                .password(password)
                .email(email)
                .role(role)
                .build();

        String url = "http://localhost:" + port + "/api/v2/joinProc";

        //when
        MvcResult result = mvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(new ObjectMapper().writeValueAsString(requestDto))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andReturn();

        long savedId = Long.parseLong(result.getResponse().getContentAsString());

        //then
        Optional<UserV2> admin = userRepository.findById(savedId);

        assertThat(admin).isNotEmpty();
        assertThat(admin.get().getUsername()).isEqualTo(username);
        assertThat(admin.get().getEmail()).isEqualTo(email);
        assertThat(admin.get().getRole()).isEqualTo(role);
    }

    @Test
    @DisplayName("관리자 유저가 회원가입을 한다.")
    void joinAdminProc() throws Exception {
        //given
        String username = "username";
        String password = "password";
        String email = "test@test.com";
        Role role = Role.ADMIN;

        UserV2SaveRequestDto requestDto = UserV2SaveRequestDto.builder()
                .username(username)
                .password(password)
                .email(email)
                .role(role)
                .build();

        String url = "http://localhost:" + port + "/api/v2/joinProc";

        //when
        MvcResult result = mvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(new ObjectMapper().writeValueAsString(requestDto))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andReturn();

        long savedId = Long.parseLong(result.getResponse().getContentAsString());

        //then
        Optional<UserV2> admin = userRepository.findById(savedId);

        assertThat(admin).isNotEmpty();
        assertThat(admin.get().getUsername()).isEqualTo(username);
        assertThat(admin.get().getEmail()).isEqualTo(email);
        assertThat(admin.get().getRole()).isEqualTo(role);
    }

    /**
     * 회원 관리 (관리자만 가능)
     */
    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("관리자가 일반 유저를 삭제한다.")
    void deleteMember() throws Exception {
        //given
        //일반 계정 저장
        String memberUsername = "test";
        String memberPassword = "test";
        String memberEmail = "test@test.com";
        Role memberRole = Role.MEMBER;

        UserV2SaveRequestDto memberSaveRequestDto = UserV2SaveRequestDto.builder()
                .username(memberUsername)
                .password(memberPassword)
                .email(memberEmail)
                .role(memberRole)
                .build();

        Long memberId = userService.joinV2(memberSaveRequestDto);

        //when
        String url = "http://localhost:" + port + "/api/v1/user/" + memberId;

        MvcResult result = mvc.perform(delete(url)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andReturn();

        //then
        Long deletedMemberId = Long.valueOf(result.getResponse().getContentAsString());
        Optional<UserV2> deletedUser = userRepository.findById(deletedMemberId);
        assertThat(deletedUser).isEmpty();
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("관리자가 매니저를 삭제한다.")
    void deleteManager() throws Exception {
        //given
        //매니저 계정 저장
        String managerUsername = "test";
        String managerPassword = "test";
        String managerEmail = "test@test.com";
        Role managerRole = Role.MANAGER;

        UserV2SaveRequestDto requestDto = UserV2SaveRequestDto.builder()
                .username(managerUsername)
                .password(managerPassword)
                .email(managerEmail)
                .role(managerRole)
                .build();

        Long managerId = userService.joinV2(requestDto);

        //when
        String url = "http://localhost:" + port + "/api/v1/user/" + managerId;

        MvcResult result = mvc.perform(delete(url)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andReturn();

        //then
        Long deletedMemberId = Long.valueOf(result.getResponse().getContentAsString());
        Optional<UserV2> deletedUser = userRepository.findById(deletedMemberId);
        assertThat(deletedUser).isEmpty();
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("관리자가 관리자를 삭제할 수 없다.")
    void deleteAdmin() throws Exception {
        //관리자 계정 저장
        String adminUsername = "test";
        String adminPassword = "test";
        String adminEmail = "test@test.com";
        Role adminRole = Role.ADMIN;

        UserV2SaveRequestDto adminSaveRequestDto = UserV2SaveRequestDto.builder()
                .username(adminUsername)
                .password(adminPassword)
                .email(adminEmail)
                .role(adminRole)
                .build();

        Long adminId = userService.joinV2(adminSaveRequestDto);

        String url = "http://localhost:" + port + "/api/v1/user/" + adminId;
        mvc.perform(delete(url)
                        .with(csrf()))
                .andExpect(status().isBadRequest());
    }

    /**
     * 각 권한별 페이지 접속
     */
    @Test
    @WithAuthUser(role = Role.MEMBER)
    @DisplayName("일반 유저 권한이 있는 경우 일반 유저 페이지에 접속할 수 있다.")
    void accessMemberPage() throws Exception {
        String url = "http://localhost" + port + "/member";

        mvc.perform(get(url))
                .andExpect(status().isOk());
    }

    @Test
    @WithAuthUser(role = Role.MANAGER)
    @DisplayName("매니저 권한이 있는 경우 매니저 페이지에 접속할 수 있다.")
    void accessManagerPage() throws Exception {
        String url = "http://localhost" + port + "/manager";

        mvc.perform(get(url))
                .andExpect(status().isOk());
    }

    @Test
    @WithAuthUser(role = Role.ADMIN)
    @DisplayName("관리자 권한이 있는 경우 관리자 페이지에 접속할 수 있다.")
    void accessAdminPage() throws Exception {
        String url = "http://localhost" + port + "/admin";

        mvc.perform(get(url))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = {"MANAGER", "ADMIN"})
    @DisplayName("일반 유저 권한이 없는 경우 일반 유저 페이지 접속 시 에러 페이지가 나온다.")
    void accessDeniedMemberPage() throws Exception {
        String url = "http://localhost" + port + "/member";

        mvc.perform(get(url))
                .andExpect(status().isForbidden())
                .andExpect(forwardedUrl("/errors/denied"));
    }

    @Test
    @WithMockUser(roles = {"MEMBER", "ADMIN"})
    @DisplayName("매니저 권한이 없는 경우 매니저 페이지 접속 시 에러 페이지가 나온다.")
    void accessDeniedManagerPage() throws Exception {
        String url = "http://localhost" + port + "/manager";

        mvc.perform(get(url))
                .andExpect(status().isForbidden())
                .andExpect(forwardedUrl("/errors/denied"));
    }

    @Test
    @WithMockUser(roles = {"MEMBER", "MANAGER"})
    @DisplayName("관리자 권한이 없는 경우 관리자 페이지 접속 시 에러 페이지가 나온다.")
    void accessDeniedAdminPage() throws Exception {
        String url = "http://localhost" + port + "/admin";

        /*
        redirect : URL 변화 O, 객체 재사용 X (브라우저가 요청을 변경하여 다시 리다이렉트 페이지로 요청을 보내는 것)
        forward : URL 변화 X, 객체 재사용 O (해당 페이지에서 처리하지 못해 다른 페이지로 바로 전환하는 것)
         */
        mvc.perform(get(url))
                .andExpect(status().isForbidden())
                .andExpect(forwardedUrl("/errors/denied"));
    }


}