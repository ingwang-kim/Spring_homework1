package com.sparta.hanghaememo.dto;

import lombok.Getter;

@Getter
public class MemoRequestDto {
    private String username;
    private String contents;
    private String pw;
    private String title;
    private Long id;
}
