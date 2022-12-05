package com.sparta.hanghaememo.repository;


import com.sparta.hanghaememo.entity.Memo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemoRepository extends JpaRepository<Memo, Long> {
    List<Memo> findAllByOrderByModifiedAtDesc();

    List<Memo> findAllByUsername(String userName);

    List<Memo> findByUserId(Long userId);
    Optional<Memo> deleteByIdAndUserId(Long id, Long userId);

    Optional<Memo> findByIdAndUserId(Long id, Long userId);
}