package com.elevenhelevenm.practice.board.domain.board.dto;

import com.elevenhelevenm.practice.board.domain.board.model.Board;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class BoardMapperTest {

    @Test
    @DisplayName("DTO에서 Entity로 변환한다.")
    void dtoToEntity() {
        //given
        BoardDto boardDto = BoardDto.builder()
                .id(1L)
                .title("title")
                .content("content")
                .author("author")
                .build();

        //when
        Board board = BoardMapper.INSTANCE.toEntity(boardDto);

        //then
        assertThat(board.getTitle()).isEqualTo(boardDto.getTitle());
        assertThat(board.getContent()).isEqualTo(boardDto.getContent());
        assertThat(board.getAuthor()).isEqualTo(boardDto.getAuthor());
    }

    @Test
    @DisplayName("Entity에서 DTO로 변환한다.")
    void entityToDto() {
        //given
        Board board = Board.builder()
                .id(1L)
                .title("title")
                .content("content")
                .author("author")
                .build();

        //when
        BoardDto boardDto = BoardMapper.INSTANCE.toDto(board);

        //then
        assertThat(boardDto.getId()).isEqualTo(board.getId());
        assertThat(boardDto.getTitle()).isEqualTo(board.getTitle());
        assertThat(boardDto.getContent()).isEqualTo(board.getContent());
        assertThat(boardDto.getAuthor()).isEqualTo(board.getAuthor());
    }

    @Test
    @DisplayName("DTO로부터 Entity 값이 변경된다.")
    void updateEntityFromDto() {
        //given
        Long id = 1L;
        String title = "title";
        String content = "content";
        String author = "author";
        Board board = Board.builder()
                .id(id)
                .title(title)
                .content(content)
                .author(author)
                .build();

        Long updateId = 2L;
        String updateTitle = "update title";
        String updateContent = "update content";
        String updateAuthor = "update author";
        BoardDto boardDto = BoardDto.builder()
                .id(updateId)
                .title(updateTitle)
                .content(updateContent)
                .author(updateAuthor)
                .build();

        //when
        BoardMapper.INSTANCE.updateBoardFromDto(boardDto, board);

        //then (id, author는 변경되지 않는다.)
        assertThat(board.getId()).isEqualTo(id);
        assertThat(board.getTitle()).isEqualTo(updateTitle);
        assertThat(board.getContent()).isEqualTo(updateContent);
        assertThat(board.getAuthor()).isEqualTo(author);
    }
}