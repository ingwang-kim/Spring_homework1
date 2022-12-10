package com.sparta.hanghaememo.repository;

import com.sparta.hanghaememo.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Long> {

    Optional<Users> findByUsername(String name);

}
