package com.sparta.hanghaememo.repository;

import com.sparta.hanghaememo.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment,Long> {

}
