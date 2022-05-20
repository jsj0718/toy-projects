package com.elevenhelevenm.practice.board.domain.board.dto;

import com.elevenhelevenm.practice.board.domain.board.model.Board;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring") //스프링 빈으로 등록하기 위한 설정
public interface BoardMapper {

    BoardMapper INSTANCE = Mappers.getMapper(BoardMapper.class);

    @Mapping(target = "id", ignore = true)
    Board toEntity(BoardDto boardDto);

    BoardDto toDto(Board board);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "author", ignore = true)
    void updateBoardFromDto(BoardDto boardDto, @MappingTarget Board board);

}
