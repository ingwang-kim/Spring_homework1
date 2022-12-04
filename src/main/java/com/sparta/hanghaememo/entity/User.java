package com.sparta.hanghaememo.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.*;

@Getter
@NoArgsConstructor
@Entity(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // nullable: null 허용 여부
    // unique: 중복 허용 여부 (false 일때 중복 허용)
    @Column(nullable = false, unique = true)
    @Size(min = 4,max = 10,message ="아이디의 길이는 4자에서 10자 사이입니다")
    @Pattern(regexp = "[a-z0-9]*$",message = "아이디 형식이 일치하지 않습니다")
    private String username;

    @Column(nullable = false)
    @Size(min = 8,max = 15,message ="비밀번호의 길이는 8자에서 15자 사이입니다")
    @Pattern(regexp = "[a-zA-Z0-9]*$",message = "비밀번호 형식이 일치하지 않습니다")
    private String pw;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    public User(String username, String pw, UserRoleEnum role) {
        this.username = username;
        this.pw = pw;
        this.role = role;
    }

}