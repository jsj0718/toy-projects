package com.elevenhelevenm.practice.board.domain.user.dto.request;

import com.elevenhelevenm.practice.board.domain.user.model.Admin;
import com.elevenhelevenm.practice.board.domain.user.model.Manager;
import com.elevenhelevenm.practice.board.domain.user.model.Member;
import com.elevenhelevenm.practice.board.domain.user.model.Role;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Data
public class UserV2SaveRequestDto {

    @NotNull(message = "아이디는 필수사항이며, 널은 허용되지 않습니다.")
    @Pattern(regexp = "[\\S]+", message = "아이디는 필수사항이며, 공백은 포함될 수 없습니다.")
    private String username;

    @NotNull(message = "비밀번호는 필수사항이며, 널은 허용되지 않습니다.")
    @Pattern(regexp = "[\\S]+", message = "비밀번호는 필수사항이며, 공백은 포함될 수 없습니다.")
    private String password;

    @Email(message = "이메일 주소가 유효하지 않습니다.")
    @NotNull(message = "이메일은 필수사항이며, 널은 허용되지 않습니다.")
    @Pattern(regexp = "[\\S]+", message = "이메일은 필수사항이며, 공백은 포함될 수 없습니다.")
    private String email;

    @NotNull(message = "권한은 필수사항이며, 널은 허용되지 않습니다.")
    private Role role;

    @Builder
    public UserV2SaveRequestDto(String username, String password, String email, Role role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    public Member toMember() {
        return Member.builder()
                .username(username)
                .password(password)
                .email(email)
                .role(role)
                .build();
    }

    public Manager toManager() {
        return Manager.builder()
                .username(username)
                .password(password)
                .email(email)
                .role(role)
                .build();
    }

    public Admin toAdmin() {
        return Admin.builder()
                .username(username)
                .password(password)
                .email(email)
                .role(role)
                .build();
    }
}
