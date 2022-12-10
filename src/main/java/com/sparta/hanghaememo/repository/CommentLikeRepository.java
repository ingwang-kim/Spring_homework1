package com.sparta.hanghaememo.repository;

import com.sparta.hanghaememo.entity.Comment;
import com.sparta.hanghaememo.entity.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {


    Optional<CommentLike> findByComment(Comment comment);

    void deleteByUsersIdAndCommentId(Long usersId,Long commentId);
}