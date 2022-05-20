package com.elevenhelevenm.practice.board.domain.user.dto.request;

import com.elevenhelevenm.practice.board.domain.user.model.Role;
import com.elevenhelevenm.practice.board.domain.user.model.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Data
public class UserSaveRequestDto {

    @Pattern(regexp = "[\\S]+", message = "아이디는 필수사항이며, 공백은 포함될 수 없습니다.")
    private String username;

    @Pattern(regexp = "[\\S]+", message = "비밀번호는 필수사항이며, 공백은 포함될 수 없습니다.")
    private String password;

    @Email(message = "이메일 주소가 유효하지 않습니다.")
    @Pattern(regexp = "[\\S]+", message = "이메일은 필수사항이며, 공백은 포함될 수 없습니다.")
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
