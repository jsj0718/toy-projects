package com.elevenhelevenm.practice.board.domain.grid.model;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum WorkType {

    UPLOAD("업로드"), DOWNLOAD("다운로드");

    private final String type;
}
