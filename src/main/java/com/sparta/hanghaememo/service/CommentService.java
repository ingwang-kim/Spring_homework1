package com.sparta.hanghaememo.service;

import com.sparta.hanghaememo.dto.CommentDto;
import com.sparta.hanghaememo.dto.ResponseMsgDto;
import com.sparta.hanghaememo.entity.*;
import com.sparta.hanghaememo.repository.CommentLikeRepository;
import com.sparta.hanghaememo.repository.CommentRepository;
import com.sparta.hanghaememo.repository.MemoRepository;
import com.sparta.hanghaememo.util.exception.ErrorCode;
import com.sparta.hanghaememo.util.exception.RequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class CommentService{


    private final MemoRepository memoRepository;
    private final CommentRepository commentRepository;
    private final CommentLikeRepository commentLikeRepository;


    public CommentDto addComment(CommentDto commentDto, Long id, User user) {


        Memo memo = memoRepository.findById(id).orElseThrow(
                () -> new RequestException(ErrorCode.게시글이_존재하지_않습니다_400)
        );
       /*     Comment comments = Comment.builder()
                    .id(commentDto.getId())
                    .memo(memo)
                    .username(users.getUsername())
                    .comment(commentDto.getComment())
                    .commentLikeCount(count)
                    .build();*/
        Comment comments = commentRepository.save(new Comment(commentDto, memo, user));

        return new CommentDto(comments);

    }
    @Transactional
    public CommentDto updateComment(Long id, CommentDto commentDto, User user) {

            Comment comment;
            //유저의 권한이 admin과 같으면 모든 데이터 수정 가능
            if(user.getRole().equals(UserRoleEnum.ADMIN)){
                comment = commentRepository.findById(id).orElseThrow(NullPointerException::new);
            }else {
                //유저의 권한이 admin이 아니면 아이디가 같은 유저만 수정 가능
                comment = commentRepository.findByIdAndUsername(id, user.getUsername()).orElseThrow(
                        () -> new RequestException(ErrorCode.아이디가_일치하지_않습니다)
                );
            }
            comment.update(commentDto);
            return new CommentDto(comment,commentLikeRepository.countAllByCommentId(comment.getId()));

    }


    public void deleteComment( Long id, User user) {

            Comment comment;
            //유저의 권한이 admin과 같으면 모든 데이터 삭제 가능
            if (user.getRole().equals(UserRoleEnum.ADMIN)) {
                    comment = commentRepository.findById(id).orElseThrow(NullPointerException::new);
                } else {
                    //유저의 권한이 admin이 아니면 아이디가 같은 유저만 삭제 가능
                    comment = commentRepository.findByIdAndUsername(id, user.getUsername()).orElseThrow(
                        () -> new RequestException(ErrorCode.아이디가_일치하지_않습니다)
                );
            }

            commentRepository.delete(comment);
        }
    //메모 좋아요 서비스
    public ResponseMsgDto CommentLikeCD(Long id, User user) {

        Comment comment = commentRepository.findById(id).orElseThrow();

        if(commentLikeRepository.findByCommentAndUserId(comment,user.getId()).isEmpty()){
            CommentLike commentLike = commentLikeRepository.save(new CommentLike(user,comment));
            comment.getCommentLikes().add(commentLike);

            /*comment.setCommentLikeCount(comment.getCommentLikeCount()+1);*/

            return new ResponseMsgDto(HttpStatus.OK.value(),"좋아요 성공");
        }else{
            commentLikeRepository.deleteByUserIdAndCommentId(user.getId(), comment.getId());
            /*comment.setCommentLikeCount(comment.getCommentLikeCount()+1);*/
            return new ResponseMsgDto(HttpStatus.OK.value(),"좋아요 취소");
        }
    }

}

