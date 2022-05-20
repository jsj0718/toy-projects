package com.elevenhelevenm.practice.board.global.errors.handler;

import com.elevenhelevenm.practice.board.global.errors.exception.CustomException;
import com.elevenhelevenm.practice.board.global.errors.dto.ErrorResponse;
import com.elevenhelevenm.practice.board.global.errors.exception.MembershipException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class ExceptionControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<Object> handleCustomException(CustomException e) {
        log.error("handleCustomException : {}", e.getErrorCode());
        return ErrorResponse.toResponseEntity(e.getErrorCode());
    }

    @ExceptionHandler(MembershipException.class)
    public ResponseEntity<Object> handleMembershipException(MembershipException e) {
        log.error("handleMembershipException : {}", e);
        return ErrorResponse.toResponseEntity(e.getErrorCode());
    }

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, Map<String, String>> errors = new HashMap<>();
        errors.put("message", new HashMap<>());

        ex.getFieldErrors().forEach(e -> {
            log.info("Field Error : {}, {}", e.getField(), e.getDefaultMessage());
            errors.get("message").put(e.getField(), e.getDefaultMessage());
        });

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }
}
