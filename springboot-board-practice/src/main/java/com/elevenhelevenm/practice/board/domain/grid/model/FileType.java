package com.elevenhelevenm.practice.board.domain.grid.model;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum FileType {

    REQUIRED_FILE("요청파일"), RESULT_FILE("결과파일");

    private final String type;
}
