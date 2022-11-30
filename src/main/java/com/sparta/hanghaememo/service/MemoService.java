package com.sparta.hanghaememo.service;

import com.sparta.hanghaememo.dto.DelResponseDto;
import com.sparta.hanghaememo.dto.MemoRequestDto;
import com.sparta.hanghaememo.dto.MemoResponseDto;
import com.sparta.hanghaememo.dto.UpdateResponseDto;
import com.sparta.hanghaememo.entity.Memo;
import com.sparta.hanghaememo.repository.MemoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemoService {
    public final MemoRepository memoRepository;

    @Transactional
    public MemoResponseDto createMemo(MemoRequestDto requestDto) {
        Memo memo = new Memo(requestDto);
        memoRepository.save(memo);
        MemoResponseDto memoResponseDto = new MemoResponseDto(memo);
        return memoResponseDto;
    }

    @Transactional
    public List<MemoResponseDto> getMemos() {
        List<Memo> memo = memoRepository.findAllByOrderByModifiedAtDesc();
        return memo.stream().map(memo1 ->new MemoResponseDto(memo1)).collect(Collectors.toList());
    }
     //리스트 형식으로 출력
    /*@Transactional
    public List<MemoResponseDto> getMemos() {
        List<Memo> memoList = memoRepository.findAllByOrderByModifiedAtDesc();
        List<MemoResponseDto> memoResponseDto = new ArrayList<>();
        for(Memo memo : memoList){
            MemoResponseDto memoDto = new MemoResponseDto(memo);
            memoResponseDto.add(memoDto);
        }
        return memoResponseDto;
    }*/

    //아이디 리스트 중 하나 출력
    @Transactional
    public MemoResponseDto openMemo(Long id){
        Memo memo = getMemo(id, "아이디가 존재하지 않습니다");
        MemoResponseDto memoResponseDto = new MemoResponseDto(memo);

        return memoResponseDto;

    }
    //update
    @Transactional
    public UpdateResponseDto update(Long id, MemoRequestDto requestDto) {
        //메모가 있는지 확인
        Memo memo = getMemo(id, "아이디가 존재하지 않습니다");
        if(requestDto.getPw().equals(memo.getPw())) {// 비밀번호 일치시 변경
            memo.update(requestDto);
        }
        return new UpdateResponseDto(memo);
    }

    //delete
    @Transactional
    public DelResponseDto deleteMemo(Long id, MemoRequestDto requestDto) {
        Memo memo = getMemo(id, "존재하지 않습니다");
        DelResponseDto delResponseDto;
        if(memo.getPw().equals(requestDto.getPw())){
            memoRepository.deleteById(id);
            delResponseDto = new DelResponseDto(true);
            return delResponseDto;
        }
        else {
            delResponseDto = new DelResponseDto(false);
            return delResponseDto ;
        }

    }

    // memo값이 null값인지 확인하는 메소드
    private Memo getMemo(Long id, String 존재하지_않습니다) {
        Memo memo=memoRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException()
        );
        return memo;
    }
}
