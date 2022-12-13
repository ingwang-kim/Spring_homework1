package com.sparta.hanghaememo.service;

import com.sparta.hanghaememo.dto.ResponseMsgDto;
import com.sparta.hanghaememo.entity.Comment;
import com.sparta.hanghaememo.entity.CommentLike;
import com.sparta.hanghaememo.entity.User;
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

    public ResponseMsgDto CommentLikeCD(Long id, User user) {

        Comment comment = commentRepository.findById(id).orElseThrow();

        if(commentLikeRepository.findByCommentAndUserId(comment,user.getId()).isEmpty()){
            CommentLike commentLike = commentLikeRepository.save(new CommentLike(user,comment));
            comment.getCommentLikes().add(commentLike);

            comment.setCommentLikeCount(comment.getCommentLikeCount()+1);

            return new ResponseMsgDto(HttpStatus.OK.value(),"좋아요 성공");
        }else{
            commentLikeRepository.deleteByUserIdAndCommentId(user.getId(), comment.getId());
            comment.setCommentLikeCount(comment.getCommentLikeCount()+1);
            return new ResponseMsgDto(HttpStatus.OK.value(),"좋아요 취소");
        }
    }
}
