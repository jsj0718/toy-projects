package com.elevenhelevenm.practice.board.domain.user.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Role {
    
    USER("ROLE_USER", "사용자"),
    MEMBER("ROLE_MEMBER", "일반 유저"),
    MANAGER("ROLE_MANAGER", "매니저"),
    ADMIN("ROLE_ADMIN", "관리자"),
    SUPER_ADMIN("ROLE_SUPER_ADMIN", "수퍼 관리자"),
    ;

    @JsonCreator
    public static Role from(String s) {
        return Role.valueOf(s.toUpperCase());
    }
    
    private final String key;
    private final String title;
}
