package com.sparta.hanghaememo.service;

import com.sparta.hanghaememo.dto.CommentDto;
import com.sparta.hanghaememo.dto.MemoRequestDto;
import com.sparta.hanghaememo.dto.MemoResponseDto;
import com.sparta.hanghaememo.dto.ResponseMsgDto;
import com.sparta.hanghaememo.entity.*;
import com.sparta.hanghaememo.repository.CommentLikeRepository;
import com.sparta.hanghaememo.repository.MemoLikeRepository;
import com.sparta.hanghaememo.repository.MemoRepository;
import com.sparta.hanghaememo.util.exception.ErrorCode;
import com.sparta.hanghaememo.util.exception.RequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemoService {
    private final MemoRepository memoRepository;
    private final CommentLikeRepository commentLikeRepository;

    private final MemoLikeRepository memoLikeRepository;




    //데이터 생성
    @Transactional
    public MemoResponseDto createMemo(MemoRequestDto requestDto, User user) {


        Memo memo = memoRepository.save(new Memo(requestDto, user));
        List<CommentDto> commentDtoList = new ArrayList<>();
        for (Comment comment : memo.getCommentList()) {
            commentDtoList.add(new CommentDto(comment));
        }
        return new MemoResponseDto(memo, commentDtoList);
    }


    @Transactional
    public List<MemoResponseDto> getMemos() {
        List<Memo> memoList = memoRepository.findAllByOrderByModifiedAtDesc();
        List<MemoResponseDto> memoResponseDto = new ArrayList<>();

        for(Memo memo : memoList){
            List<CommentDto> commentDtoList = new ArrayList<>();
            for(Comment comment : memo.getCommentList()){
                commentDtoList.add(new CommentDto(comment,commentLikeRepository.countAllByCommentId(comment.getId())));
            }
            MemoResponseDto memoDto = new MemoResponseDto(memo,commentDtoList);
            memoResponseDto.add(memoDto);
        }
        return memoResponseDto;
    }

    //아이디 리스트 중 하나 출력
    @Transactional
    public MemoResponseDto openMemo(Long id) {

        Memo memo = memoRepository.findById(id).orElseThrow(
                () -> new RequestException(ErrorCode.게시글이_존재하지_않습니다_400)
        );
        List<CommentDto> commentDtoList = new ArrayList<>();
        for (Comment comment : memo.getCommentList()) {
            commentDtoList.add(new CommentDto(comment,commentLikeRepository.countAllByCommentId(comment.getId())));
        }
        return new MemoResponseDto(memo, commentDtoList);
    }


    //update
    @Transactional
    public MemoResponseDto update(Long id, MemoRequestDto requestDto, User user) {
        Memo memo;
        //유저의 권한이 admin과 같으면 모든 데이터 수정 가능
        if (user.getRole().equals(UserRoleEnum.ADMIN)) {
            memo = memoRepository.findById(id).orElseThrow(
                    () -> new RequestException(ErrorCode.게시글이_존재하지_않습니다_400)
            );
        } else {
            //유저의 권한이 admin이 아니면 아이디가 같은 유저만 수정 가능
            memo = memoRepository.findByIdAndUsername(id, user.getUsername()).orElseThrow(
                    () -> new RequestException(ErrorCode.아이디가_일치하지_않습니다)
            );
        }

        List<CommentDto> commentDtoList = new ArrayList<>();
        for (Comment comment : memo.getCommentList()) {
            commentDtoList.add(new CommentDto(comment));
        }

        memo.update(requestDto);

        return new MemoResponseDto(memo,commentDtoList);
    }

    //delete
    @Transactional
    public void deleteMemo(Long id, User user) {
            Memo memo;
            //유저의 권한이 admin과 같으면 모든 데이터 삭제 가능
            if (user.getRole().equals(UserRoleEnum.ADMIN)) {
                memo = memoRepository.findById(id).orElseThrow(
                        () -> new RequestException(ErrorCode.게시글이_존재하지_않습니다_400)
                );
            } else {
                //유저의 권한이 admin이 아니면 아이디가 같은 유저만 삭제 가능
                memo = memoRepository.findByIdAndUsername(id, user.getUsername()).orElseThrow(
                        () -> new RequestException(ErrorCode.아이디가_일치하지_않습니다)
                );
            }
            memoRepository.delete(memo);
    }
    @Transactional
    public ResponseMsgDto MemoLikeCD(Long id, User user) {

        Memo memo = memoRepository.findById(id).orElseThrow();


        if (memoLikeRepository.findByMemoAndUserId(memo,user.getId()).isEmpty()) {
            memoLikeRepository.save(new MemoLike(user, memo));
            return new ResponseMsgDto(HttpStatus.OK.value(), "좋아요 성공");
        } else {
            memoLikeRepository.deleteByUserIdAndMemoId(user.getId(), memo.getId());
            return new ResponseMsgDto(HttpStatus.OK.value(), "좋아요 취소");
        }
    }


}
