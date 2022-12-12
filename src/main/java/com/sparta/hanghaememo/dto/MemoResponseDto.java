package com.sparta.hanghaememo.dto;

import com.sparta.hanghaememo.entity.Comment;
import com.sparta.hanghaememo.entity.Memo;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class MemoResponseDto{
    private String username;
    private String contents;
    private String title;
    private Long id;
    private int MemoLikeCount;

    private List<Comment> commentList = new ArrayList<>();
    /*private List<CommentDto> commentDtoList = new ArrayList<>();*/

    /*for(Comment comment )*/




    public MemoResponseDto(Memo memo) {
        this.id = memo.getId();
        this.username = memo.getUsername();
        this.contents = memo.getContents();
        this.title = memo.getTitle();
        this.MemoLikeCount = memo.getMemoLikes().size();
        this.commentList = memo.getCommentList();

    }
}
