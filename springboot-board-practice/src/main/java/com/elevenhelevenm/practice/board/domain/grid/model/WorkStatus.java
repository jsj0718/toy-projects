package com.elevenhelevenm.practice.board.domain.grid.model;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum WorkStatus {

    COMPLETE("완료"), INCOMPLETE("미완료");

    private final String status;
}
