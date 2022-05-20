package com.elevenhelevenm.practice.board.global.errors.exception;

import com.elevenhelevenm.practice.board.global.errors.code.MembershipErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MembershipException extends RuntimeException {

    private final MembershipErrorCode errorCode;
}
