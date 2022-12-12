package com.sparta.hanghaememo.repository;

import com.sparta.hanghaememo.entity.Memo;
import com.sparta.hanghaememo.entity.MemoLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemoLikeRepository extends JpaRepository<MemoLike,Long> {

    Optional<MemoLike> findByMemo(Memo memo);
    Optional<MemoLike> findByMemoAndUserId(Memo memo , Long userid);

    void deleteByUserIdAndMemoId(Long userId,Long memoId);
}
