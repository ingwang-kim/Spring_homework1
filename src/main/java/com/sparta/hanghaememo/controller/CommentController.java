package com.sparta.hanghaememo.controller;

import com.sparta.hanghaememo.dto.CommentDto;
import com.sparta.hanghaememo.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/comment/{id}")
    public CommentDto addComment(@RequestBody CommentDto commentDto, @PathVariable Long id) {
        // 응답 보내기
        return commentService.addComment(commentDto,id);
    }


}
