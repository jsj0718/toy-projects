package com.elevenhelevenm.practice.board.domain.grid.dto;

import com.elevenhelevenm.practice.board.domain.grid.model.GridData;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface GridDataMapper {

    GridDataMapper INSTANCE = Mappers.getMapper(GridDataMapper.class);

    @Mapping(target = "id", ignore = true)
    GridData toEntity(GridDataDto gridDataDto);

    GridDataDto toDto(GridData gridData);
}
