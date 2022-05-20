package com.elevenhelevenm.practice.board.config.security;

import com.elevenhelevenm.practice.board.domain.user.model.Role;
import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithAuthUserSecurityContextFactory.class)
public @interface WithAuthUser {
    String username() default "test";
    String password() default "test";
    String email() default "test@test.com";
    Role role() default Role.MEMBER;
}
