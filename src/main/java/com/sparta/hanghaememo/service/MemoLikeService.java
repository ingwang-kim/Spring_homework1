package com.sparta.hanghaememo.service;

import com.sparta.hanghaememo.dto.ResponseMsgDto;
import com.sparta.hanghaememo.entity.Memo;
import com.sparta.hanghaememo.entity.MemoLike;
import com.sparta.hanghaememo.entity.User;
import com.sparta.hanghaememo.repository.MemoLikeRepository;
import com.sparta.hanghaememo.repository.MemoRepository;
import com.sparta.hanghaememo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class MemoLikeService {

    private final MemoLikeRepository memoLikeRepository;
    private final MemoRepository memoRepository;
    private final UserRepository userRepository;

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

