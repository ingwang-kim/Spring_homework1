package com.sparta.hanghaememo.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserInfoRequestDto {
    private String username;
    private String pw;
}