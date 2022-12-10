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

    @PostMapping("/api/CommentLike/{id}")
    public ResponseMsgDto addLike(@PathVariable Long id,@AuthenticationPrincipal UserDetailsImpl userDetails){
        return commentLikeService.addLike(id,userDetails.getUser());

    }


}
