package com.sparta.hanghaememo.controller;

import com.sparta.hanghaememo.dto.CommentDto;
import com.sparta.hanghaememo.dto.ResponseMsgDto;
import com.sparta.hanghaememo.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/api/comment/{id}")
    public CommentDto addComment(@RequestBody CommentDto commentDto, @PathVariable Long id, HttpServletRequest request) {
        // 응답 보내기
        return commentService.addComment(commentDto,id,request);
    }

    @PutMapping("/api/comment/{id}")
    public CommentDto updateComment(@PathVariable Long id,@RequestBody CommentDto commentDto, HttpServletRequest request){
        return commentService.updateComment(id,commentDto,request);
    }
    @DeleteMapping("/api/comment/{id}")
    public ResponseMsgDto deleteComment(@PathVariable Long id, HttpServletRequest request){
        commentService.deleteComment(id,request);
        return new ResponseMsgDto(HttpStatus.OK.value(), "댓글 삭제 성공");

    }
}
