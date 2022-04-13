package com.elevenhelevenm.practice.board.web.dto.response.board;

import com.elevenhelevenm.practice.board.domain.board.Board;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Data
public class BoardResponseDto {

    private Long id;

    private String title;

    private String content;

    private String author;

    public BoardResponseDto(Long id, String title, String content, String author) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public BoardResponseDto(Board board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.author = board.getAuthor();
    }
}
