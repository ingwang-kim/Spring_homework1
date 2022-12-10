package com.sparta.hanghaememo.service;

import com.sparta.hanghaememo.entity.Comment;
import com.sparta.hanghaememo.entity.CommentLike;
import com.sparta.hanghaememo.entity.Users;
import com.sparta.hanghaememo.repository.CommentLikeRepository;
import com.sparta.hanghaememo.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentLikeService {

    private final CommentLikeRepository commentLikeRepository;
    private final CommentRepository commentRepository;

    public void addLike(Long id, Users users) {

        Comment comment = commentRepository.findById(id).orElseThrow();
        commentLikeRepository.save(new CommentLike(users,comment));


    }
}
