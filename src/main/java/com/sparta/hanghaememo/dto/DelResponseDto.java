package com.sparta.hanghaememo.dto;

import com.sparta.hanghaememo.entity.Memo;

public class DelResponseDto {
    private String msg;

    public DelResponseDto(Memo memo){
        this.msg = "삭제되었습니다";
    }
}
