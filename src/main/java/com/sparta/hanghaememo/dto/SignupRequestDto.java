package com.sparta.hanghaememo.dto;

import com.sparta.hanghaememo.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignupRequestDto {
    private String username;
    private String pw;
    private boolean admin = false;
    private String adminToken = "";


    public SignupRequestDto(User user){
        this.username = user.getUsername();
        this.pw = user.getPw();
    }
}
