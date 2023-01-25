package com.elevenhelevenm.practice.board.exception.errorcode;

import org.springframework.http.HttpStatus;

public interface ErrorCode {

    HttpStatus getHttpStatus();

    String getDescription();

    String name();
}