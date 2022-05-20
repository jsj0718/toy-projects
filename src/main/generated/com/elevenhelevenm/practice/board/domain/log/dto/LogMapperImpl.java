package com.elevenhelevenm.practice.board.domain.log.dto;

import com.elevenhelevenm.practice.board.domain.log.dto.LogDto.LogDtoBuilder;
import com.elevenhelevenm.practice.board.domain.log.model.LoginLog;
import com.elevenhelevenm.practice.board.domain.log.model.LoginLog.LoginLogBuilder;
import com.elevenhelevenm.practice.board.domain.log.model.PageLog;
import com.elevenhelevenm.practice.board.domain.log.model.PageLog.PageLogBuilder;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-05-16T11:00:10+0900",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11 (Oracle Corporation)"
)
@Component
public class LogMapperImpl implements LogMapper {

    @Override
    public LoginLog toLoginLog(LogDto logDto) {
        if ( logDto == null ) {
            return null;
        }

        LoginLogBuilder<?, ?> loginLog = LoginLog.builder();

        loginLog.username( logDto.getUsername() );
        loginLog.ip( logDto.getIp() );
        loginLog.exception( logDto.getException() );
        loginLog.isSuccessLogin( logDto.getIsSuccessLogin() );

        loginLog.loggingDate( java.time.LocalDateTime.now() );

        return loginLog.build();
    }

    @Override
    public PageLog toPageLog(LogDto logDto) {
        if ( logDto == null ) {
            return null;
        }

        PageLogBuilder<?, ?> pageLog = PageLog.builder();

        pageLog.username( logDto.getUsername() );
        pageLog.ip( logDto.getIp() );
        pageLog.exception( logDto.getException() );
        pageLog.url( logDto.getUrl() );

        pageLog.loggingDate( java.time.LocalDateTime.now() );

        return pageLog.build();
    }

    @Override
    public LogDto toDto(LoginLog log) {
        if ( log == null ) {
            return null;
        }

        LogDtoBuilder<?, ?> logDto = LogDto.builder();

        logDto.id( log.getId() );
        logDto.username( log.getUsername() );
        logDto.ip( log.getIp() );
        logDto.loggingDate( log.getLoggingDate() );
        logDto.isSuccessLogin( log.getIsSuccessLogin() );
        logDto.exception( log.getException() );

        return logDto.build();
    }

    @Override
    public LogDto toDto(PageLog log) {
        if ( log == null ) {
            return null;
        }

        LogDtoBuilder<?, ?> logDto = LogDto.builder();

        logDto.id( log.getId() );
        logDto.username( log.getUsername() );
        logDto.ip( log.getIp() );
        logDto.loggingDate( log.getLoggingDate() );
        logDto.url( log.getUrl() );
        logDto.exception( log.getException() );

        return logDto.build();
    }
}
