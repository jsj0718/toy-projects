package com.elevenhelevenm.practice.board.exception.handler;

import com.elevenhelevenm.practice.board.exception.UserException;
import com.elevenhelevenm.practice.board.web.dto.response.error.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class ExceptionControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {UserException.class})
    protected ResponseEntity<ErrorResponse> handleUserException(UserException e) {
        log.error("handleCustomException throw UserException : {}", e.getErrorCode());
        return ErrorResponse.toResponseEntity(e.getErrorCode());
    }

}
