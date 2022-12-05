package com.sparta.hanghaememo.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MemoRequestDto {
    private String username;
    private String contents;
    private String pw;
    private String title;
    private String role;
    private Long id;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
