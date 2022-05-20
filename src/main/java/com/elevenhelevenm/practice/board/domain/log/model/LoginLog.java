package com.elevenhelevenm.practice.board.domain.log.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Getter
@SuperBuilder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("LOGIN_LOG")
@Entity
public class LoginLog extends LogV2 {

    private Boolean isSuccessLogin;

}
