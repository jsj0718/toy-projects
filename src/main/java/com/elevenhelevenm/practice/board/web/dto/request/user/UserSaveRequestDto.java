package com.elevenhelevenm.practice.board.web.dto.request.user;

import com.elevenhelevenm.practice.board.domain.user.Role;
import com.elevenhelevenm.practice.board.domain.user.User;
import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@RequiredArgsConstructor
@Data
public class UserSaveRequestDto {

    @NotBlank
    private String username;

    @NotBlank(message = "비밀번호는 필수 사항입니다.")
    private String password;

    @Email(message = "이메일 주소가 유효하지 않습니다.")
    private String email;

    @Builder
    public UserSaveRequestDto(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public User toEntity() {
        return User.builder()
                .username(username)
                .password(password)
                .email(email)
                .role(Role.USER)
                .build();
    }
}
