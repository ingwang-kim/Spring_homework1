package com.sparta.hanghaememo.controller;


import com.sparta.hanghaememo.dto.ResponseMsgDto;
import com.sparta.hanghaememo.security.UserDetailsImpl;
import com.sparta.hanghaememo.service.CommentLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CommentLikeController {
    private final CommentLikeService commentLikeService;

    @PostMapping("/api/Like/Comment/{id}")
    public ResponseMsgDto CommentLikeCD(@PathVariable Long id,@AuthenticationPrincipal UserDetailsImpl userDetails){
        return commentLikeService.CommentLikeCD(id,userDetails.getUser());

    }


}
