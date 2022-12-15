package com.sparta.hanghaememo.service;

import com.sparta.hanghaememo.dto.SignupRequestDto;
import com.sparta.hanghaememo.dto.UserInfoRequestDto;
import com.sparta.hanghaememo.entity.User;
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
        Optional<User> found = userRepository.findByUsername(username);
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
        User user = new User(username, pw, role);
        userRepository.save(user);
    }

    //로그인
    @Transactional(readOnly = true)
    public void login(UserInfoRequestDto userInfoRequestDto, HttpServletResponse response) {
        String username = userInfoRequestDto.getUsername();
        String pw = userInfoRequestDto.getPw();

        // 사용자 확인
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new RequestException(ErrorCode.아이디가_일치하지_않습니다
                )
        );
        // 비밀번호 확인
        if(!passwordEncoder.matches(pw, user.getPw())){
            throw  new RequestException(ErrorCode.비밀번호가_일치하지_않습니다_400);
        }

        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getUsername(), user.getRole()));
        //addHeader로 헤더에 값 넣어주기 (키, 토큰)
    }

    @Transactional
    public void signDown(UserInfoRequestDto userInfoRequestDto, User user){

            if (userRepository.findByUsername(userInfoRequestDto.getUsername()).isEmpty()) {
                throw new RequestException(ErrorCode.사용자가_존재하지_않습니다_400);
            }else {
                if(!passwordEncoder.matches(userInfoRequestDto.getPw(), user.getPw())){
                    throw new RequestException(ErrorCode.비밀번호가_일치하지_않습니다_400);
                }
                else {
                    userRepository.deleteByUsername(userInfoRequestDto.getUsername());
                }
            }


    }
}