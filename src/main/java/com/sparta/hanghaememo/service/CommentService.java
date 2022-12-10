package com.sparta.hanghaememo.service;

import com.sparta.hanghaememo.dto.CommentDto;
import com.sparta.hanghaememo.entity.Comment;
import com.sparta.hanghaememo.entity.Memo;
import com.sparta.hanghaememo.entity.Users;
import com.sparta.hanghaememo.entity.UserRoleEnum;
import com.sparta.hanghaememo.jwt.JwtUtil;
import com.sparta.hanghaememo.repository.CommentRepository;
import com.sparta.hanghaememo.repository.MemoRepository;
import com.sparta.hanghaememo.repository.UserRepository;
import com.sparta.hanghaememo.util.exception.ErrorCode;
import com.sparta.hanghaememo.util.exception.RequestException;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;


@Service
@RequiredArgsConstructor
public class CommentService{


    private final MemoRepository memoRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final JwtUtil jwtUtil;


    public CommentDto addComment(CommentDto commentDto, Long id,  HttpServletRequest request) {

        String token = jwtUtil.resolveToken(request);
        Claims claims;

        if (token != null) {

            if (jwtUtil.validateToken(token)) {
                // 토큰에서 사용자 정보 가져오기
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new RequestException(ErrorCode.유효하지_않은_토큰_400);
            }


            // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
            Users users = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new RequestException(ErrorCode.사용자가_존재하지_않습니다_400)
            );

            Memo memo = memoRepository.findById(id).orElseThrow(
                    () -> new RequestException(ErrorCode.게시글이_존재하지_않습니다_400)
            );

            Comment comments = Comment.builder()
                    .id(commentDto.getId())
                    .memo(memo)
                    .username(users.getUsername())
                    .comment(commentDto.getComment())
                    .build();


            commentRepository.save(comments);

            return new CommentDto(comments);
        }else {
            /*return new ResponseMsgDto(HttpStatus.OK.value(), "실패");*/
            throw new RequestException(ErrorCode.유효하지_않은_토큰_400);
        }

    }
    @Transactional
    public CommentDto updateComment(Long id, CommentDto commentDto, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        if (token != null) {

            if (jwtUtil.validateToken(token)) {
                // 토큰에서 사용자 정보 가져오기
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new RequestException(ErrorCode.유효하지_않은_토큰_400);
            }


            // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
            Users users = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new RequestException(ErrorCode.중복_사용자_존재_400)
            );


            Comment comment;
            //유저의 권한이 admin과 같으면 모든 데이터 수정 가능
            if(users.getRole().equals(UserRoleEnum.ADMIN)){
                comment = commentRepository.findById(id).orElseThrow(NullPointerException::new);
            }else {
                //유저의 권한이 admin이 아니면 아이디가 같은 유저만 수정 가능
                comment = commentRepository.findByIdAndUsername(id, users.getUsername()).orElseThrow(
                        () -> new RequestException(ErrorCode.아이디가_일치하지_않습니다)
                );
            }
            comment.update(commentDto);

            return new CommentDto(comment);
        }else {
            throw new RequestException(ErrorCode.유효하지_않은_토큰_400);
        }
    }


    public void deleteComment( Long id, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        if (token != null) {

            if (jwtUtil.validateToken(token)) {
                // 토큰에서 사용자 정보 가져오기
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new RequestException(ErrorCode.유효하지_않은_토큰_400);
            }


            // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
            Users users = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new RequestException(ErrorCode.사용자가_존재하지_않습니다_400)
            );
            Comment comment;
            //유저의 권한이 admin과 같으면 모든 데이터 삭제 가능
            if (users.getRole().equals(UserRoleEnum.ADMIN)) {
                comment = commentRepository.findById(id).orElseThrow(NullPointerException::new);
            } else {
                //유저의 권한이 admin이 아니면 아이디가 같은 유저만 삭제 가능
                comment = commentRepository.findByIdAndUsername(id, users.getUsername()).orElseThrow(
                        () -> new RequestException(ErrorCode.아이디가_일치하지_않습니다)
                );
            }

            commentRepository.delete(comment);
        }
    }
}

