package com.elevenhelevenm.practice.board.exception.errorcode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum BoardErrorCode implements ErrorCode {

    NotFoundBoardId(HttpStatus.BAD_REQUEST, "존재하지 않은 게시글입니다."),
    ;

    private final HttpStatus httpStatus;
    private final String description;

}
