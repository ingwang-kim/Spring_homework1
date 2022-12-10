package com.sparta.hanghaememo.repository;

import com.sparta.hanghaememo.entity.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {

    Optional<CommentLike> findBycLikeIDAndUsername(Long id, String name);
}
