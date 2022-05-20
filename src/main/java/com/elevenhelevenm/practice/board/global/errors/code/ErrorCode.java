package com.elevenhelevenm.practice.board.global.errors.code;

import org.springframework.http.HttpStatus;

public interface ErrorCode {

    HttpStatus getHttpStatus();

    String getDescription();

    String name();
}