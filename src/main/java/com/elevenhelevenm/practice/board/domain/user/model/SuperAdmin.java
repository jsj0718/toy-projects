package com.elevenhelevenm.practice.board.domain.user.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class SuperAdmin {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ip;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder
    public SuperAdmin(String ip, Role role) {
        this.ip = ip;
        this.role = role;
    }
}
