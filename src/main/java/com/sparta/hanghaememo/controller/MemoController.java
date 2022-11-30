package com.sparta.hanghaememo.controller;

import com.sparta.hanghaememo.dto.DelResponseDto;
import com.sparta.hanghaememo.dto.MemoRequestDto;
import com.sparta.hanghaememo.dto.MemoResponseDto;
import com.sparta.hanghaememo.dto.UpdateResponseDto;
import com.sparta.hanghaememo.service.MemoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemoController {



    private final MemoService memoService;

    @PostMapping("/api/memos")
    public MemoResponseDto createMemo(@RequestBody MemoRequestDto requestDto){
        return memoService.createMemo(requestDto);
    }

    @GetMapping("/api/memos")
    public List<MemoResponseDto> getMemos(){
        return memoService.getMemos();
    }

    @GetMapping("/api/open/{id}")
    public MemoResponseDto openMemo(@PathVariable Long id){
        return memoService.openMemo(id);

    }

    @PutMapping("/api/update/{id}")
    public UpdateResponseDto updateMemo(@PathVariable Long id, @RequestBody MemoRequestDto requestDto){
        return memoService.update(id,requestDto);
    }
    @DeleteMapping("/api/delete/{id}")
    public DelResponseDto deleteMemo(@PathVariable Long id, @RequestBody MemoRequestDto requestDto){
        return memoService.deleteMemo(id,requestDto);
    }

}
