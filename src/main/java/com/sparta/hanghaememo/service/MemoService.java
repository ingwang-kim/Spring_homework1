package com.sparta.hanghaememo.service;

import com.sparta.hanghaememo.dto.MemoRequestDto;
import com.sparta.hanghaememo.dto.MemoResponseDto;
import com.sparta.hanghaememo.entity.Memo;
import com.sparta.hanghaememo.repository.MemoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemoService {
    public final MemoRepository memoRepository;

    @Transactional
    public Memo createMemo(MemoRequestDto requestDto) {
        Memo memo = new Memo(requestDto);
        memoRepository.save(memo);
        return memo;
    }

    @Transactional
    public List<Memo> getMemos() {
        return memoRepository.findAllByOrderByModifiedAtDesc();
    }


    //아이디 리스트 중 하나 출력
    @Transactional
    public MemoResponseDto openMemo(Long id){
        Memo memo = getMemo(id, "아이디가 존재하지 않습니다");
        MemoResponseDto memoResponseDto = new MemoResponseDto(memo);

        return memoResponseDto;

    }

    @Transactional
    public Memo update(Long id, MemoRequestDto requestDto) {
        //메모가 있는지 확인
        Memo memo = getMemo(id, "아이디가 존재하지 않습니다");
        if(memo.getPw().equals(requestDto.getPw())) {// 비밀번호 일치시 변경
            memo.update(requestDto);
            return memo;
        }else {
            return memo; //비밀번호 불일치시 그대로 출력
        }
    }

    @Transactional
    public Long deleteMemo(Long id, MemoRequestDto requestDto) {
        Memo memo = getMemo(id, "존재하지 않습니다");
        if(memo.getPw().equals(requestDto.getPw())){
            memoRepository.deleteById(id);
            return id;
        }
        else {
            return id*0;
        }

    }

    private Memo getMemo(Long id, String 존재하지_않습니다) {
        Memo memo=memoRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException()
        );
        return memo;
    }
}
