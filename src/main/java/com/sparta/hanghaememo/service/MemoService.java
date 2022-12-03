package com.sparta.hanghaememo.service;

import com.sparta.hanghaememo.dto.DelResponseDto;
import com.sparta.hanghaememo.dto.MemoRequestDto;
import com.sparta.hanghaememo.dto.MemoResponseDto;
import com.sparta.hanghaememo.dto.UpdateResponseDto;
import com.sparta.hanghaememo.entity.Memo;
import com.sparta.hanghaememo.jwt.JwtUtil;
import com.sparta.hanghaememo.repository.MemoRepository;
import com.sparta.hanghaememo.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemoService {
    public final MemoRepository memoRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;



    //데이터 생성
    @Transactional
    public MemoResponseDto createMemo(MemoRequestDto requestDto , HttpServletRequest request) {

        String token = jwtUtil.resolveToken(request);
        Claims claims;

        /*if (token != null) {
            if (jwtUtil.validateToken(token)) {
                // 토큰에서 사용자 정보 가져오기
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }

            // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );*/
            if (token != null) {
                Memo memo = new Memo(requestDto);
                memoRepository.save(memo);
                MemoResponseDto memoResponseDto = new MemoResponseDto(memo);

                return memoResponseDto;
            } else {
                return null;
            }
    }


    //모든 데이터 뽑아오기
    /*@Transactional
    public List<MemoResponseDto> getMemos() {
        List<Memo> memo = memoRepository.findAllByOrderByModifiedAtDesc();
        return memo.stream().map(memo1 ->new MemoResponseDto(memo1)).collect(Collectors.toList());
    }*/


    //아이디 리스트 중 하나 출력
    //리스트 형식으로 출력
    @Transactional
    public List<MemoResponseDto> getMemos() {
        List<Memo> memoList = memoRepository.findAllByOrderByModifiedAtDesc();
        List<MemoResponseDto> memoResponseDto = new ArrayList<>();
        for(Memo memo : memoList){
            MemoResponseDto memoDto = new MemoResponseDto(memo);
            memoResponseDto.add(memoDto);
        }
        return memoResponseDto;
    }

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
