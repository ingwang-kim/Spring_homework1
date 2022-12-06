package com.sparta.hanghaememo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseMsgDto {
    private int statusCode;
    private String msg;



}
