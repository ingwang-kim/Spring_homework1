package com.sparta.hanghaememo.util.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@Slf4j
public class ResponseexcptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(RequestException.class)
    protected ResponseEntity<String> handleCustomException(RequestException e) {

        log.debug("Exception : '{}'", e.getMessage());
        return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
    }

    @ExceptionHandler(JsonProcessingException.class)
    protected ResponseEntity<String> jsonProcessingException(JsonProcessingException e) {

        log.debug("Exception : '{}'", e.getMessage());
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }


}