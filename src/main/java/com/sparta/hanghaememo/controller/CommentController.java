package com.sparta.hanghaememo.controller;

import com.sparta.hanghaememo.dto.CommentDto;
import com.sparta.hanghaememo.dto.ResponseMsgDto;
import com.sparta.hanghaememo.security.UserDetailsImpl;
import com.sparta.hanghaememo.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/api/comment/{id}")
    public CommentDto addComment(@RequestBody CommentDto commentDto, @PathVariable Long id,@AuthenticationPrincipal UserDetailsImpl userDetails) {
        // 응답 보내기
        return commentService.addComment(commentDto,id,userDetails.getUser());
    }

    @PutMapping("/api/comment/{id}")
    public CommentDto updateComment(@PathVariable Long id,@RequestBody CommentDto commentDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return commentService.updateComment(id,commentDto,userDetails.getUser());
    }
    @DeleteMapping("/api/comment/{id}")
    public ResponseMsgDto deleteComment(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails){
        commentService.deleteComment(id,userDetails.getUser());
        return new ResponseMsgDto(HttpStatus.OK.value(), "댓글 삭제 성공");

    }
}
