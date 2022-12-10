package com.sparta.hanghaememo.service;

import com.sparta.hanghaememo.dto.ResponseMsgDto;
import com.sparta.hanghaememo.entity.Comment;
import com.sparta.hanghaememo.entity.CommentLike;
import com.sparta.hanghaememo.entity.Users;
import com.sparta.hanghaememo.repository.CommentLikeRepository;
import com.sparta.hanghaememo.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Transactional
@Service
@RequiredArgsConstructor
public class CommentLikeService {

    private final CommentLikeRepository commentLikeRepository;
    private final CommentRepository commentRepository;

    public ResponseMsgDto addLike(Long id, Users users) {

        Comment comment = commentRepository.findById(id).orElseThrow();


        if(commentLikeRepository.findByComment(comment).isEmpty()){
            commentLikeRepository.save(new CommentLike(users,comment));
            return new ResponseMsgDto(HttpStatus.OK.value(),"좋아요 성공");
        }else{
            commentLikeRepository.deleteByUsersIdAndCommentId(users.getId(), comment.getId());
            return new ResponseMsgDto(HttpStatus.OK.value(),"좋아요 취소");
        }
    }
}
