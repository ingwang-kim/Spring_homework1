package com.sparta.hanghaememo.service;

import com.sparta.hanghaememo.dto.MemoRequestDto;
import com.sparta.hanghaememo.dto.MemoResponseDto;
import com.sparta.hanghaememo.dto.UpdateResponseDto;
import com.sparta.hanghaememo.entity.Memo;
import com.sparta.hanghaememo.entity.User;
import com.sparta.hanghaememo.entity.UserRoleEnum;
import com.sparta.hanghaememo.jwt.JwtUtil;
import com.sparta.hanghaememo.repository.MemoRepository;
import com.sparta.hanghaememo.repository.UserRepository;
import com.sparta.hanghaememo.util.exception.ErrorCode;
import com.sparta.hanghaememo.util.exception.RequestException;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
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

        if (token != null) {

            if (jwtUtil.validateToken(token)) {
                // 토큰에서 사용자 정보 가져오기
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }


            // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );

            Memo memo = memoRepository.save(new Memo(requestDto, user.getId(),user.getUsername()));
            return new MemoResponseDto(memo);

        } else {
            throw new RequestException(ErrorCode.BAD_TOKKEN_400);
        }
    }


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
    public MemoResponseDto openMemo(Long id) {

            Memo memo = getMemo(id, "아이디가 존재하지 않습니다");
            MemoResponseDto memoResponseDto = new MemoResponseDto(memo);

            return memoResponseDto;
    }


    //update
    @Transactional
    public UpdateResponseDto update(Long id, MemoRequestDto requestDto, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        // 토큰이 있는 경우에만 관심상품 조회 가능
        if (token != null) {
            // Token 검증
            if (jwtUtil.validateToken(token)) {
                // 토큰에서 사용자 정보 가져오기
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }

            // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );
            Memo memo;
            //유저의 권한이 admin과 같으면 모든 데이터 수정 가능
            if(user.getRole().equals(UserRoleEnum.ADMIN)){
                memo = memoRepository.findById(id).orElseThrow(NullPointerException::new);
            }else {
                //유저의 권한이 admin이 아니면 아이디가 같은 유저만 수정 가능
                memo = memoRepository.findByIdAndUserId(id, user.getId()).orElseThrow(
                        () -> new NullPointerException("아이디가 일치하지 않습니다.")
                );
            }
            memo.update(requestDto);

            return new UpdateResponseDto(memo);
        }else {
            throw new RequestException(ErrorCode.BAD_TOKKEN_400);
        }
    }

    //delete
    @Transactional
    public void deleteMemo(Long id,HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        // 토큰이 있는 경우에만 관심상품 조회 가능
        if (token != null) {
            // Token 검증
            if (jwtUtil.validateToken(token)) {
                // 토큰에서 사용자 정보 가져오기
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }

            // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );

            Memo memo;
            //유저의 권한이 admin과 같으면 모든 데이터 삭제 가능
            if (user.getRole().equals(UserRoleEnum.ADMIN)) {
                memo = memoRepository.findById(id).orElseThrow(NullPointerException::new);
            } else {
                //유저의 권한이 admin이 아니면 아이디가 같은 유저만 삭제 가능
                memo = memoRepository.findByIdAndUserId(id, user.getId()).orElseThrow(
                        () -> new NullPointerException("아이디가 일치하지 않습니다.")
                );
            }

            memoRepository.delete(memo);

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
