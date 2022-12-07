package com.sparta.hanghaememo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sparta.hanghaememo.dto.CommentDto;
import com.sparta.hanghaememo.dto.CommentRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Table(name = "COMMENT")
public class Comment extends Timestamped {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)//로딩
    @JoinColumn(name = "MEMO_ID")
    private Memo memo;


    @Column(nullable = false)
    private String comment;

    @Column(nullable = false)
    private String username;



    public Comment(CommentRequestDto requestDto, String username){
        this.comment = requestDto.getComment();
        this.username = username;
    }

    public void update(CommentDto commentdto){
        this.comment = commentdto.getComment();

    }


}
