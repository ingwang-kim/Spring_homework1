package com.sparta.hanghaememo.controller;

import com.sparta.hanghaememo.dto.LoginRequestDto;
import com.sparta.hanghaememo.dto.ResponseMsgDto;
import com.sparta.hanghaememo.dto.SignupRequestDto;
import com.sparta.hanghaememo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
        return ResponseEntity.ok(new ResponseMsgDto(HttpStatus.OK,"가입완료"));
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
    public ResponseEntity<ResponseMsgDto> login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {//클라이언트에 반환하기 위해 response객체
        userService.login(loginRequestDto, response);

        return ResponseEntity.ok(new ResponseMsgDto(HttpStatus.OK,"로그인 성공"));
    }
}