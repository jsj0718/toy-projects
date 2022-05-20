package com.elevenhelevenm.practice.board.domain.log.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@SuperBuilder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter @Setter
public class LogDto {

    private Long id;

    private String username;

    private String ip;

    private LocalDateTime loggingDate;

    private String url;

    private Boolean isSuccessLogin;

    private String exception;
}
