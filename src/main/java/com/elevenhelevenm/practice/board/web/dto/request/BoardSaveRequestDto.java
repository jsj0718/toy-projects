package com.elevenhelevenm.practice.board.web.dto.request;

import com.elevenhelevenm.practice.board.domain.board.Board;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class BoardSaveRequestDto {

    private String title;
    private String content;
    private String author;

    public Board toEntity() {
        return Board.builder()
                .title(title)
                .content(content)
                .author(author)
                .build();
    }

}
