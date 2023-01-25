package com.elevenhelevenm.practice.board.global.errors.exception;

import com.elevenhelevenm.practice.board.global.errors.code.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CustomException extends RuntimeException {

    private final ErrorCode errorCode;
}
