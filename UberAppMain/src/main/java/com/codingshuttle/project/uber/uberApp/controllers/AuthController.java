package com.codingshuttle.project.uber.uberApp.controllers;

import com.codingshuttle.project.uber.uberApp.dto.*;
import com.codingshuttle.project.uber.uberApp.services.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping(path ="/signUp")
    ResponseEntity<UserDto> signUp(@RequestBody SignupDto signupDto){
        return new  ResponseEntity<>(authService.signUp(signupDto), HttpStatus.CREATED);
    }

    @Secured("ROLE_ADMIN")
    @PostMapping(path="/onBoardNewDriver/{userId}")
    ResponseEntity<DriverDto> onBoardNewDriver(@PathVariable Long userId, @RequestBody OnBoardDriverDto onBoardDriverDto){
        return new ResponseEntity<>(authService.onboardNewDriver(userId,onBoardDriverDto.getVehicleId()),HttpStatus.CREATED);
    }


    @PostMapping(path = "/login")
    ResponseEntity<LoginResponseDto> login(@RequestBody  LoginRequestDto loginRequestDto, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){

        String token[] =authService.login(loginRequestDto.getEmail(),loginRequestDto.getPassword());

        Cookie cookie =new Cookie("token",token[1]);
        cookie.setHttpOnly(true);

        httpServletResponse.addCookie(cookie);

        return  ResponseEntity.ok(new LoginResponseDto(token[0]));
    }
}
