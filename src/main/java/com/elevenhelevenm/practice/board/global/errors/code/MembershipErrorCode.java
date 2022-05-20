package com.elevenhelevenm.practice.board.global.errors.code;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Getter
@RequiredArgsConstructor
public enum MembershipErrorCode implements ErrorCode {

    DUPLICATED_MEMBERSHIP_REGISTER(BAD_REQUEST, "해당 멤버십에 중복된 아이디가 존재합니다."),
    MEMBERSHIP_NOT_FOUND(NOT_FOUND, "해당 멤버십을 찾을 수 없습니다."),
    NOT_MEMBERSHIP_OWNER(BAD_REQUEST, "해당 멤버십 아이디가 아닙니다."),
    ;

    private final HttpStatus httpStatus;
    private final String description;

}
