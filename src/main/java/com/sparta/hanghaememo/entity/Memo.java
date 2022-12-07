package com.sparta.hanghaememo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sparta.hanghaememo.dto.MemoRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "MEMO")
public class Memo extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @OneToMany(mappedBy = "memo") //외래키 이름으로 mappedBy
    @OrderBy("createdAt desc")
    private List<Comment> commentList= new ArrayList<>();




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