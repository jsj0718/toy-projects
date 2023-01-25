package com.elevenhelevenm.practice.board.domain.user.dto;

import com.elevenhelevenm.practice.board.domain.common.dto.BaseTimeDto;
import com.elevenhelevenm.practice.board.domain.user.model.Role;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@SuperBuilder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter @Setter
public class UserDto extends BaseTimeDto implements Serializable {
    private Long id;

    private String username;

    private String password;

    private String email;

    private Role role;

}
