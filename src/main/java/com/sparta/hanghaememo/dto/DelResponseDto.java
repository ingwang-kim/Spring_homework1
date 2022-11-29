package com.sparta.hanghaememo.dto;

import lombok.Getter;

@Getter
public class DelResponseDto {
    private String msg;
    private Boolean success;


    public DelResponseDto(boolean success){
        this.success = success;
        if(success==true){
            this.msg = "success";
        }else {
            this.msg="failed";
        }
    }
}
