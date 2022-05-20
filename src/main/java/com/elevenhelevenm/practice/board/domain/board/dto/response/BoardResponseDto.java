package com.elevenhelevenm.practice.board.domain.board.dto.response;

import com.elevenhelevenm.practice.board.domain.board.model.Board;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Data
public class BoardResponseDto {

    private Long id;

    private String title;

    private String content;

    private String author;

    private String modifiedDate;

    public BoardResponseDto(Board board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.author = board.getAuthor();
        this.modifiedDate = board.getModifiedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss"));
    }
}
