package com.elevenhelevenm.practice.board.exception.errorcode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ValidErrorCode implements ErrorCode {

    FieldNotValid(HttpStatus.BAD_REQUEST, "유효하지 않은 값입니다. 다시 입력해주세요."),
    ;

    private final HttpStatus httpStatus;
    private final String description;

}
