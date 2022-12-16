package com.sparta.hanghaememo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemoRequestDto {

    private String contents;
    private String title;
    private String username;
    private String category;
}
