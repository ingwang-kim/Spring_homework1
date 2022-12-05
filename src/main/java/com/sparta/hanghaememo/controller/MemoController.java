package com.sparta.hanghaememo.controller;

import com.sparta.hanghaememo.dto.*;
import com.sparta.hanghaememo.service.MemoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemoController {


    //memoservice를 가져다 쓰겠다 (의존성 주입)
    private final MemoService memoService;

    //메모 포스트하기
    @PostMapping("/api/memos")
    public MemoResponseDto createMemo(@RequestBody MemoRequestDto requestDto, HttpServletRequest request){
        return memoService.createMemo(requestDto, request);
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
    public UpdateResponseDto updateMemo(@PathVariable Long id, @RequestBody MemoRequestDto requestDto ,HttpServletRequest request){
        return memoService.update(id,requestDto,request);
    }

    //아이디가 일치하는 데이터 삭제
    @DeleteMapping("/api/delete/{id}")
    public ResponseEntity<ResponseMsgDto> deleteMemo(@PathVariable Long id, HttpServletRequest request){
        memoService.deleteMemo(id,request);
        return ResponseEntity.ok(new ResponseMsgDto(HttpStatus.OK.value(), "삭제 완료"));
    }

    /*@PostMapping("/api/comment/{id}")
    public CommentResponseDto postComment(@PathVariable Long id, @RequestBody MemoRequestDto requestDto, HttpServletRequest request){
        return memoService.postComment(id,requestDto,request);
    }*/
}
