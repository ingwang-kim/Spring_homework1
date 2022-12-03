package com.sparta.hanghaememo.service;

import com.sparta.hanghaememo.dto.LoginRequestDto;
import com.sparta.hanghaememo.dto.SignupRequestDto;
import com.sparta.hanghaememo.entity.User;
import com.sparta.hanghaememo.entity.UserRoleEnum;
import com.sparta.hanghaememo.jwt.JwtUtil;
import com.sparta.hanghaememo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

import static com.sparta.hanghaememo.jwt.JwtUtil.AUTHORIZATION_HEADER;


@Service
@RequiredArgsConstructor
public class UserService {



    // ADMIN_TOKEN
    private static final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    private final UserRepository userRepository ;


    private final JwtUtil jwtUtil; // jwtUtil 사용을 위해 의존성 주입

    @Transactional
    public void signup(SignupRequestDto signupRequestDto) {
        String username = signupRequestDto.getUsername();
        String pw = signupRequestDto.getPw();

        // 회원 중복 확인
        Optional<User> found = userRepository.findByUsername(username);
        if (found.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }

        // 사용자 ROLE 확인
        UserRoleEnum role = UserRoleEnum.USER;
        if (signupRequestDto.isAdmin()) {
            if (!signupRequestDto.getAdminToken().equals(ADMIN_TOKEN)) {
                throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능합니다.");
            }
            role = UserRoleEnum.ADMIN;
        }
        User user = new User(username, pw, role);
        userRepository.save(user);
        /*SignupResponseDto signupResponseDto = new SignupResponseDto();*/

       /* return new ResponseMsgDto(userRepository.save(user));*/
    }

    /* @Transactional(readOnly = true)
     public void login(LoginRequestDto loginRequestDto) {
         String username = loginRequestDto.getUsername();
         String password = loginRequestDto.getPassword();

         // 사용자 확인
         User user = userRepository.findByUsername(username).orElseThrow(
                 () -> new IllegalArgumentException("등록된 사용자가 없습니다.")
         );

         // 비밀번호 확인
         if(!user.getPassword().equals(password)){
             throw  new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
         }
     }
 */
    @Transactional(readOnly = true)
    public void login(LoginRequestDto loginRequestDto, HttpServletResponse response) {
        String username = loginRequestDto.getUsername();
        String pw = loginRequestDto.getPw();

        // 사용자 확인
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("등록된 사용자가 없습니다.")
        );
        // 비밀번호 확인
        if(!user.getPw().equals(pw)){
            throw  new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        response.addHeader(AUTHORIZATION_HEADER, jwtUtil.createToken(user.getUsername(), user.getRole()));
        //add header로 헤더에 값 넣어주기 (키, 토큰)
    }
}