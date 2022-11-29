package com.sparta.hanghaememo.dto;

import com.sparta.hanghaememo.entity.Memo;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MemoResponseDto{
    private String username;
    private String contents;
    private String title;
    private Long id;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;



    public MemoResponseDto(Memo memo) {
        this.id = memo.getId();
        this.username = memo.getUsername();
        this.contents = memo.getContents();
        this.title = memo.getTitle();
        this.modifiedAt = memo.getCreatedAt();
        this.createdAt = memo.getCreatedAt();

    }
}
