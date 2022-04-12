package com.elevenhelevenm.practice.board.exception;

import com.elevenhelevenm.practice.board.exception.errorcode.UserErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserException extends RuntimeException {

    private final UserErrorCode errorCode;
}
