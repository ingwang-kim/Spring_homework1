package com.sparta.hanghaememo.repository;

import com.sparta.hanghaememo.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Optional<Comment> findByIdAndUsername(Long id, String name);

}
