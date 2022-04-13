package com.elevenhelevenm.practice.board.web.dto.request.user;

import com.elevenhelevenm.practice.board.domain.user.Role;
import com.elevenhelevenm.practice.board.domain.user.User;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class UserSaveRequestDto {

    private String username;
    private String password;
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
