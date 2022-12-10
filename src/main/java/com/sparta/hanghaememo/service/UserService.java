package com.sparta.hanghaememo.service;

import com.sparta.hanghaememo.dto.LoginRequestDto;
import com.sparta.hanghaememo.dto.SignupRequestDto;
import com.sparta.hanghaememo.entity.Users;
import com.sparta.hanghaememo.entity.UserRoleEnum;
import com.sparta.hanghaememo.jwt.JwtUtil;
import com.sparta.hanghaememo.repository.UserRepository;
import com.sparta.hanghaememo.util.exception.ErrorCode;
import com.sparta.hanghaememo.util.exception.RequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserService {



    // ADMIN_TOKEN
    private static final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    private final UserRepository userRepository ;


    private final JwtUtil jwtUtil; // jwtUtil 사용을 위해 의존성 주입

    private final PasswordEncoder passwordEncoder;

    //회원가입
    @Transactional
    public void signup(SignupRequestDto signupRequestDto) {
        String username = signupRequestDto.getUsername();
        String pw = passwordEncoder.encode(signupRequestDto.getPw());

        // 회원 중복 확인
        Optional<Users> found = userRepository.findByUsername(username);
        if (found.isPresent()) {
            throw new RequestException(ErrorCode.중복된_아이디_입니다_400);
        }

        // 사용자 ROLE 확인
        UserRoleEnum role = UserRoleEnum.USER;
        if (signupRequestDto.isAdmin()) {
            if (!signupRequestDto.getAdminToken().equals(ADMIN_TOKEN)) {
                throw  new RequestException(ErrorCode.관리자_비밀번호가_일치하지_않습니다_400);
            }
            role = UserRoleEnum.ADMIN;
        }
        Users users = new Users(username, pw, role);
        userRepository.save(users);
    }

    //로그인
    @Transactional(readOnly = true)
    public void login(LoginRequestDto loginRequestDto, HttpServletResponse response) {
        String username = loginRequestDto.getUsername();
        String pw = loginRequestDto.getPw();

        // 사용자 확인
        Users users = userRepository.findByUsername(username).orElseThrow(
                () -> new RequestException(ErrorCode.아이디가_일치하지_않습니다
                )
        );
        // 비밀번호 확인
        if(!passwordEncoder.matches(pw, users.getPw())){
            throw  new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(users.getUsername(), users.getRole()));
        //add header로 헤더에 값 넣어주기 (키, 토큰)
    }
}