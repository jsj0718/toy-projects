package com.elevenhelevenm.practice.board.web.dto.request.board;

import com.elevenhelevenm.practice.board.domain.board.Board;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Data
public class BoardSaveRequestDto {

    @NotBlank(message = "게시글 제목은 필수사항입니다.")
    private String title;

    @NotBlank(message = "게시글 내용은 필수사항입니다.")
    private String content;

    @NotBlank(message = "작성자는 필수사항입니다.")
    private String author;

    @Builder
    public BoardSaveRequestDto(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public Board toEntity() {
        return Board.builder()
                .title(title)
                .content(content)
                .author(author)
                .build();
    }

}
