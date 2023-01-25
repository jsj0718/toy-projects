package com.elevenhelevenm.practice.board.domain.user.service;

import com.elevenhelevenm.practice.board.domain.user.dto.request.UserV2SaveRequestDto;
import com.elevenhelevenm.practice.board.domain.user.model.Role;
import com.elevenhelevenm.practice.board.domain.user.model.UserV2;
import com.elevenhelevenm.practice.board.domain.user.repository.UserRepositoryV2;
import com.elevenhelevenm.practice.board.global.errors.code.UserErrorCode;
import com.elevenhelevenm.practice.board.global.errors.exception.CustomException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    BCryptPasswordEncoder passwordEncoder;

    @Mock
    UserRepositoryV2<UserV2> userRepository;

    @InjectMocks
    UserService userService;

    @Test
    @DisplayName("동일한 유저 이름이 존재하면 가입 되지 않는다.")
    void duplicatedId() {
        //given
        String username = "test";
        String password = "test";
        String email = "test@test.com";
        Role role = Role.MEMBER;

        UserV2SaveRequestDto requestDto = UserV2SaveRequestDto.builder()
                .username(username)
                .password(password)
                .email(email)
                .role(role)
                .build();

        Optional<UserV2> member = Optional.of(requestDto.toMember());

        given(passwordEncoder.encode(password)).willReturn("encodedPassword");

        given(userRepository.findByUsername(username)).willReturn(member);

        //when
        CustomException e = assertThrows(CustomException.class, () -> userService.joinV2(requestDto));

        //then
        assertThat(e.getErrorCode()).isEqualTo(UserErrorCode.FoundDuplicatedId);
    }

    @Test
    @DisplayName("동일한 이메일로 가입된 아이디가 존재하면 가입 되지 않는다.")
    void duplicatedEmail() {
        //given
        String username = "test";
        String password = "test";
        String email = "test@test.com";
        Role role = Role.MEMBER;

        UserV2SaveRequestDto requestDto = UserV2SaveRequestDto.builder()
                .username(username)
                .password(password)
                .email(email)
                .role(role)
                .build();

        Optional<UserV2> member = Optional.of(requestDto.toMember());

        given(passwordEncoder.encode(password)).willReturn("encodedPassword");

        given(userRepository.findByUsername(username)).willReturn(Optional.empty());

        given(userRepository.findByEmail(email)).willReturn(member);

        //when
        CustomException e = assertThrows(CustomException.class, () -> userService.joinV2(requestDto));

        //then
        assertThat(e.getErrorCode()).isEqualTo(UserErrorCode.FoundDuplicatedEmail);
    }
}