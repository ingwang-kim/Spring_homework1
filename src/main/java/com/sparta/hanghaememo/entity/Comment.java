package com.sparta.hanghaememo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sparta.hanghaememo.dto.CommentDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "COMMENT")
public class Comment extends Timestamped {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)//로딩
    @JoinColumn(name = "MEMO_ID")
    @JsonIgnore
    private Memo memo;

    @Column(nullable = false)
    private String comment;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private int commentLikeCount;


    @OneToMany(mappedBy = "comment")
    private List<CommentLike> commentLikes = new ArrayList<>();



    public Comment(CommentDto commentdto, Memo memo, User user){
        this.comment = commentdto.getComment();
        this.memo = memo;
        this.username = user.getUsername();
    }


    public void update(CommentDto commentdto){
        this.comment = commentdto.getComment();

    }

    public void setCommentLikeCount(int num){
        this.commentLikeCount = num;
    }


}
