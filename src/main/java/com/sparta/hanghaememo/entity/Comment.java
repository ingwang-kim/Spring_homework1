package com.sparta.hanghaememo.entity;

import com.sparta.hanghaememo.dto.MemoRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class Comment extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private Long userId;




    public Comment(MemoRequestDto requestDto , Long id) {
        this.username = requestDto.getUsername();
        this.title = requestDto.getTitle();
        this.userId=id;
    }
}
