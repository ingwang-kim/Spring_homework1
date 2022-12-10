package com.sparta.hanghaememo.dto;

import com.sparta.hanghaememo.entity.CommentLike;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentLikeDto {
    private Long cId;

    private String userName;

    public CommentLikeDto(CommentLike commentLike){
        this.cId = commentLike.getComment().getId();
        this.userName=commentLike.getUsername();

    }

}
