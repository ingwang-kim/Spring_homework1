package com.sparta.hanghaememo.entity;

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

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String contents;

    @Column(nullable = false)
    private String title;

    @Column
    private String category;

    @OneToMany(mappedBy = "memo",cascade = CascadeType.REMOVE) //테이블 이름으로 mappedBy
    @OrderBy("createdAt desc")
    private List<Comment> commentList= new ArrayList<>();

    @OneToMany(mappedBy = "memo",cascade = CascadeType.REMOVE)
    private List<MemoLike> memoLikes=new ArrayList<>();



    public Memo(MemoRequestDto memoRequestDto , User user) {

        this.username = user.getUsername();
        this.contents = memoRequestDto.getContents();
        this.title = memoRequestDto.getTitle();
        this.category=memoRequestDto.getCategory();
        this.user = user;

    }
    public void update(MemoRequestDto memoRequestDto) {

        this.title = memoRequestDto.getTitle();
        this.username = memoRequestDto.getUsername();
        this.contents = memoRequestDto.getContents();
        this.category= memoRequestDto.getCategory();

    }



}