package com.elevenhelevenm.practice.board.domain.board.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Data
public class BoardUpdateRequestDto {

    @NotBlank(message = "게시글 제목은 필수사항입니다.")
    private String title;

    @NotBlank(message = "게시글 내용은 필수사항입니다.")
    private String content;

    @Builder
    public BoardUpdateRequestDto(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
