package com.elevenhelevenm.practice.board.exception.handler;

import com.elevenhelevenm.practice.board.exception.CustomException;
import com.elevenhelevenm.practice.board.exception.errorcode.ValidErrorCode;
import com.elevenhelevenm.practice.board.web.dto.response.error.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class ExceptionControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<Object> handleCustomException(CustomException e) {
        log.error("handleCustomException : {}", e.getErrorCode());
        return ErrorResponse.toResponseEntity(e.getErrorCode());
    }

    // TODO: 2022-04-14 Validation Handler 구현하기 
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("handleMethodArgumentNotValidException");

        StringBuilder sb = new StringBuilder();
        for (ObjectError error : ex.getAllErrors()) {
            sb.append(error.getDefaultMessage());
            sb.append("\n");
        }

        return ErrorResponse.toResponseEntity(ValidErrorCode.FieldNotValid, sb.toString());
    }
}
