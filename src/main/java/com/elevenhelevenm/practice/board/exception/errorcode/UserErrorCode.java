package com.elevenhelevenm.practice.board.exception.errorcode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements ErrorCode {

    FoundDuplicatedId(BAD_REQUEST, "중복된 아이디가 존재합니다."),
    FoundDuplicatedEmail(BAD_REQUEST, "이미 동일한 이메일로 가입한 아이디가 존재합니다."),
    ;

    private final HttpStatus httpStatus;
    private final String description;
}
