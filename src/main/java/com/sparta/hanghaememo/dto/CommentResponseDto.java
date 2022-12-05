package com.sparta.hanghaememo.dto;

import com.sparta.hanghaememo.entity.Memo;
import lombok.Getter;

import java.time.LocalDateTime;

    @Getter
    public class CommentResponseDto {
        private String username;
        private String contents;
        private Long id;
        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;


        public CommentResponseDto(Memo memo) {
            this.id = memo.getId();
            this.username = memo.getUsername();
            this.contents = memo.getContents();
            this.modifiedAt = memo.getCreatedAt();
            this.createdAt = memo.getCreatedAt();

        }
    }
