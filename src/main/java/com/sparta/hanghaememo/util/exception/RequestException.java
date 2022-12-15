package com.sparta.hanghaememo.util.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
/*@ResponseStatus(code = Http)*/
public class RequestException extends RuntimeException {

    private final HttpStatus httpStatus;

    public RequestException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.httpStatus = errorCode.getHttpStatus();
    }
}