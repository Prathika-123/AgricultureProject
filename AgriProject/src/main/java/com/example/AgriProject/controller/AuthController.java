package com.example.AgriProject.controller;

import com.example.AgriProject.dto.LoginRequestDto;
import com.example.AgriProject.dto.LoginResponseDto;
import com.example.AgriProject.dto.SignupRequestDto;
import com.example.AgriProject.dto.SignupResponseDto;
import com.example.AgriProject.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:63342")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/api/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto){
        System.out.println("Login page");
        try{
            LoginResponseDto responseDto=authService.login(loginRequestDto);
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        }catch (IllegalArgumentException exception){
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/api/signup")
    public ResponseEntity<SignupResponseDto> signup(@RequestBody SignupRequestDto signupRequestDto){
        System.out.println("Sign up page");
        try{
            SignupResponseDto responseDto=authService.signup(signupRequestDto);
            return new ResponseEntity<>(responseDto,HttpStatus.CREATED);
        }catch (IllegalArgumentException exception){
            return ResponseEntity.badRequest().build();
        }
    }
}
