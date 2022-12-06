package com.sparta.hanghaememo.dto;

import com.sparta.hanghaememo.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {

    private Long id;
    private Long mId; //메모 아이디
    private String username;
    private String comment;

    public CommentDto(Comment comment){

        this.id= comment.getId();
        this.mId = comment.getMemo().getId();
        this.username = comment.getUsername();
        this.comment = comment.getComment();

    }
}
