package com.elevenhelevenm.practice.board.domain.log.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Getter 
@SuperBuilder
@AllArgsConstructor(access = AccessLevel.PRIVATE) //빌더
@NoArgsConstructor(access = AccessLevel.PROTECTED) //프록시
@Entity
public class Log {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String ip;

    private String loginUrl;

    private LocalDateTime loginDate;

    private Boolean isSuccessLogin;

    private String exception;

}
