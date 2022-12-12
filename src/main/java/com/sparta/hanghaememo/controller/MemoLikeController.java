package com.sparta.hanghaememo.controller;

import com.sparta.hanghaememo.dto.ResponseMsgDto;
import com.sparta.hanghaememo.security.UserDetailsImpl;
import com.sparta.hanghaememo.service.MemoLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemoLikeController {

    private final MemoLikeService memoLikeService;

    @PostMapping("/api/Like/Memo/{id}")
    public ResponseMsgDto MemoLikeCD(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return memoLikeService.MemoLikeCD(id, userDetails.getUser());
    }


}
