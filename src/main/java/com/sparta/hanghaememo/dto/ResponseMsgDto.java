package com.sparta.hanghaememo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ResponseMsgDto {
    private int statusCode;
    private String msg;

    public ResponseMsgDto(int statusCode, String msg){
        this.statusCode = statusCode;
        this.msg = msg;
    }
}
