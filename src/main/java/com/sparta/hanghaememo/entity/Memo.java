package com.sparta.hanghaememo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sparta.hanghaememo.dto.MemoRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class Memo extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String contents;

    @JsonIgnore
    @Column
    private String pw;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private Long userId;



    public Memo(MemoRequestDto requestDto , Long id, String username ) {
        this.username = username;

        this.contents = requestDto.getContents();
        this.pw = requestDto.getPw();
        this.title = requestDto.getTitle();
        this.userId=id;
    }
    public void update(MemoRequestDto memoRequestDto) {

        this.title = memoRequestDto.getTitle();
        this.username = memoRequestDto.getUsername();
        this.contents = memoRequestDto.getContents();
        this.pw = memoRequestDto.getPw();

    }



}