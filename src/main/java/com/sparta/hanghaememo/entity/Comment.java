package com.sparta.hanghaememo.entity;

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
public class Comment extends Timestamped {

    @Id
    @GeneratedValue(strategy =  GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)//로딩
    @JoinColumn(name = "mId")
    private Memo memo;


    @Column(nullable = false)
    private String comment;

    @Column(nullable = false)
    private String username;



    public Comment(CommentRequestDto requestDto, String username){
        this.comment = requestDto.getComment();
        this.username = username;
    }

}
