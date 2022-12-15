package com.sparta.hanghaememo.repository;

import com.sparta.hanghaememo.entity.Comment;
import com.sparta.hanghaememo.entity.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {

    int countAllByCommentId(Long commentId);
    Optional<CommentLike> findByCommentAndUserId(Comment comment , Long userid);
    void deleteByUserIdAndCommentId(Long userId,Long commentId);


}
