package com.sparta.hanghaememo.repository;

import com.sparta.hanghaememo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String name);
    Optional<User> findByPw(String pw);
    Optional<User> findByUsernameAndPw(String username, String password);
    void deleteByUsername(String username);

}
