package com.sparta.hanghaememo.service;

import com.sparta.hanghaememo.dto.CommentDto;
import com.sparta.hanghaememo.entity.Comment;
import com.sparta.hanghaememo.entity.Memo;
import com.sparta.hanghaememo.jwt.JwtUtil;
import com.sparta.hanghaememo.repository.CommentRepositroy;
import com.sparta.hanghaememo.repository.MemoRepository;
import com.sparta.hanghaememo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class CommentService {


    public final MemoRepository memoRepository;
    private final UserRepository userRepository;
    private final CommentRepositroy commentRepositroy;
    private final JwtUtil jwtUtil;


    public CommentDto addComment(CommentDto commentDto, Long id) {


        Optional<Memo> optionalMemo = memoRepository.findById(id);
        Memo memo = optionalMemo.orElseThrow(
                () -> new IllegalArgumentException("게시글이 존재하지 않습니다.")
        );

        Comment comment = Comment.builder()
                .id(commentDto.getId())
                .memo(memo)
                .username(commentDto.getUsername())
                .comment(commentDto.getComment())
                .build();


        return new CommentDto(commentRepositroy.save(comment));


    }
}

