package com.elevenhelevenm.practice.board.domain.board.dto;

import com.elevenhelevenm.practice.board.domain.board.dto.BoardDto.BoardDtoBuilder;
import com.elevenhelevenm.practice.board.domain.board.model.Board;
import com.elevenhelevenm.practice.board.domain.board.model.Board.BoardBuilder;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-05-16T11:00:09+0900",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11 (Oracle Corporation)"
)
@Component
public class BoardMapperImpl implements BoardMapper {

    @Override
    public Board toEntity(BoardDto boardDto) {
        if ( boardDto == null ) {
            return null;
        }

        BoardBuilder<?, ?> board = Board.builder();

        board.createdDate( boardDto.getCreatedDate() );
        board.modifiedDate( boardDto.getModifiedDate() );
        board.title( boardDto.getTitle() );
        board.content( boardDto.getContent() );
        board.author( boardDto.getAuthor() );

        return board.build();
    }

    @Override
    public BoardDto toDto(Board board) {
        if ( board == null ) {
            return null;
        }

        BoardDtoBuilder<?, ?> boardDto = BoardDto.builder();

        boardDto.createdDate( board.getCreatedDate() );
        boardDto.modifiedDate( board.getModifiedDate() );
        boardDto.id( board.getId() );
        boardDto.title( board.getTitle() );
        boardDto.content( board.getContent() );
        boardDto.author( board.getAuthor() );

        return boardDto.build();
    }

    @Override
    public void updateBoardFromDto(BoardDto boardDto, Board board) {
        if ( boardDto == null ) {
            return;
        }

        board.setTitle( boardDto.getTitle() );
        board.setContent( boardDto.getContent() );
    }
}
