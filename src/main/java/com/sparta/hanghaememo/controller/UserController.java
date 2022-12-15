package com.sparta.hanghaememo.controller;

import com.sparta.hanghaememo.dto.ResponseMsgDto;
import com.sparta.hanghaememo.dto.SignupRequestDto;
import com.sparta.hanghaememo.dto.UserInfoRequestDto;
import com.sparta.hanghaememo.security.UserDetailsImpl;
import com.sparta.hanghaememo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    //회원가입
    @PostMapping("/signup")
    public ResponseEntity<ResponseMsgDto> signup(@RequestBody SignupRequestDto signupRequestDto) {
            userService.signup(signupRequestDto);
            /* return ResponseEntity.ok(signupRequestDto);*/
            return ResponseEntity.ok(new ResponseMsgDto(HttpStatus.OK.value(),"가입완료"));
    }
    // 로그인
    //form태그로 넘어왔기 때문에 ModelAttribute형식으로 받아와 @RequestBody 필요x
/*    @PostMapping("/login")
    public String login(LoginRequestDto loginRequestDto) {
        userService.login(loginRequestDto);
        return "redirect:/api/shop";
    }*/

    @ResponseBody
    @PostMapping("/login")
    public ResponseEntity<ResponseMsgDto> login(@RequestBody UserInfoRequestDto userInfoRequestDto, HttpServletResponse response) {//클라이언트에 반환하기 위해 response객체
        userService.login(userInfoRequestDto, response);

        return ResponseEntity.ok(new ResponseMsgDto(HttpStatus.OK.value(),"로그인 성공"));
    }

    @ResponseBody
    @DeleteMapping("/signDown")
    public ResponseMsgDto signDown(@RequestBody UserInfoRequestDto userInfoRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        userService.signDown(userInfoRequestDto,userDetails.getUser());
        return new ResponseMsgDto(HttpStatus.OK.value(),"회원 탈퇴 완료");
    }
}