package com.sparta.hanghaememo.util.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
@Getter
@AllArgsConstructor
public enum ErrorCode {

    COMMON_BAD_REQUEST_400(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
    BAD_TOKKEN_400(HttpStatus.BAD_REQUEST, "토큰이 유효하지 않습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}