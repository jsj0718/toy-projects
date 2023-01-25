package com.elevenhelevenm.practice.board.global.errors.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.FORBIDDEN;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements ErrorCode {

    FoundDuplicatedId(BAD_REQUEST, "중복된 아이디가 존재합니다."),
    FoundDuplicatedEmail(BAD_REQUEST, "이미 동일한 이메일로 가입한 아이디가 존재합니다."),
    FailToDeleteAdmin(BAD_REQUEST, "관리자 계정은 삭제할 수 없습니다."),
    InvalidRoleType(FORBIDDEN, "유효하지 않은 권한입니다."),
    InvalidAuthentication(FORBIDDEN, "로그인 상태가 아닙니다."),
    NoAuthorization(FORBIDDEN, "권한이 부여되지 않은 계정의 요청입니다."),
    ;

    private final HttpStatus httpStatus;
    private final String description;
}
