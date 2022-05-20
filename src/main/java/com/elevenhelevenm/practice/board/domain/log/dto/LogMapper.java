package com.elevenhelevenm.practice.board.domain.log.dto;

import com.elevenhelevenm.practice.board.domain.log.model.LoginLog;
import com.elevenhelevenm.practice.board.domain.log.model.PageLog;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface LogMapper {

    LogMapper INSTANCE = Mappers.getMapper(LogMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "loggingDate", expression = "java(java.time.LocalDateTime.now())")
    LoginLog toLoginLog(LogDto logDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "loggingDate", expression = "java(java.time.LocalDateTime.now())")
    PageLog toPageLog(LogDto logDto);

    @Mapping(target = "url", ignore = true)
    LogDto toDto(LoginLog log);

    @Mapping(target = "isSuccessLogin", ignore = true)
    LogDto toDto(PageLog log);

}
