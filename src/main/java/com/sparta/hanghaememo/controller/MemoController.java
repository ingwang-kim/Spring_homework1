package com.sparta.hanghaememo.controller;

import com.sparta.hanghaememo.dto.MemoRequestDto;
import com.sparta.hanghaememo.dto.MemoResponseDto;
import com.sparta.hanghaememo.dto.ResponseMsgDto;
import com.sparta.hanghaememo.security.UserDetailsImpl;
import com.sparta.hanghaememo.service.MemoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemoController {


    //memoservice를 가져다 쓰겠다 (의존성 주입)
    private final MemoService memoService;

    //메모 포스트하기
    @PostMapping("/api/memos")
    public MemoResponseDto createMemo(@RequestBody MemoRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return memoService.createMemo(requestDto, userDetails.getUser());
    }
    //모든 데이터 뽑아오기
    @GetMapping("/api/memos")
    public List<MemoResponseDto> getMemos(){
        return memoService.getMemos();
    }

    //아이디별 하나의 자료뽑아오기
    @GetMapping("/api/open/{id}")
    public MemoResponseDto openMemo(@PathVariable Long id){
        return memoService.openMemo(id);

    }

    //아이디가 일치하는 데이터 업데이트
    @PutMapping("/api/update/{id}")
    public MemoResponseDto updateMemo(@PathVariable Long id, @RequestBody MemoRequestDto requestDto , @AuthenticationPrincipal UserDetailsImpl userDetails){
        return memoService.update(id,requestDto,userDetails.getUser());
    }

    //아이디가 일치하는 데이터 삭제
    @DeleteMapping("/api/delete/{id}")
    public ResponseMsgDto deleteMemo(@PathVariable Long id,  @AuthenticationPrincipal UserDetailsImpl userDetails){
        memoService.deleteMemo(id,userDetails.getUser());
        return new ResponseMsgDto(HttpStatus.OK.value(), "삭제 완료");
    }

}
