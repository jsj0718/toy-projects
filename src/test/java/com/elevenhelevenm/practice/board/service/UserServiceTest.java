package com.elevenhelevenm.practice.board.service;

import com.elevenhelevenm.practice.board.domain.user.User;
import com.elevenhelevenm.practice.board.exception.CustomException;
import com.elevenhelevenm.practice.board.exception.errorcode.UserErrorCode;
import com.elevenhelevenm.practice.board.repository.UserRepository;
import com.elevenhelevenm.practice.board.web.dto.request.user.UserSaveRequestDto;
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
    UserRepository userRepository;

    @InjectMocks
    UserService userService;

    @Test
    @DisplayName("동일한 유저 이름이 존재하면 가입 되지 않는다.")
    void duplicatedId() {
        //given
        String username = "test";
        String password = "test";
        String email = "test@test.com";

        UserSaveRequestDto requestDto = UserSaveRequestDto.builder()
                .username(username)
                .password(password)
                .email(email)
                .build();

        Optional<User> user = Optional.of(requestDto.toEntity());

        given(passwordEncoder.encode(password)).willReturn("encodedPassword");

        given(userRepository.findByUsername(username)).willReturn(user);

        //when
        CustomException e = assertThrows(CustomException.class, () -> {
            userService.join(requestDto);
        });

        //then
        assertThat(e.getErrorCode()).isEqualTo(UserErrorCode.FoundDuplicatedId);
    }

    @Test
    @DisplayName("이메일로 가입된 아이디가 존재하면 가입 되지 않는다.")
    void duplicatedEmail() {
        //given
        String username = "test";
        String password = "test";
        String email = "test@test.com";

        UserSaveRequestDto requestDto = UserSaveRequestDto.builder()
                .username(username)
                .password(password)
                .email(email)
                .build();

        Optional<User> user = Optional.of(requestDto.toEntity());

        given(passwordEncoder.encode(password)).willReturn("encodedPassword");

        given(userRepository.findByUsername(username)).willReturn(Optional.empty());

        given(userRepository.findByEmail(email)).willReturn(user);

        //when
        CustomException e = assertThrows(CustomException.class, () -> {
            userService.join(requestDto);
        });

        //then
        assertThat(e.getErrorCode()).isEqualTo(UserErrorCode.FoundDuplicatedEmail);
    }
}