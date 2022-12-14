package com.sparta.hanghaememo.dto;

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
    private String category;

    private List<CommentDto> commentDtoList = new ArrayList<>();






    public MemoResponseDto(Memo memo, List<CommentDto> commentDtoList) {
        this.id = memo.getId();
        this.username = memo.getUsername();
        this.contents = memo.getContents();
        this.title = memo.getTitle();
        this.category=  memo.getCategory();
        this.MemoLikeCount = memo.getMemoLikes().size();
        this.commentDtoList = commentDtoList;

    }


}
