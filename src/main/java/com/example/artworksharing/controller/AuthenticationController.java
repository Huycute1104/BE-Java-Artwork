package com.example.artworksharing.controller;

import com.example.artworksharing.Request.UserRequest.CreatUserRequest;
import com.example.artworksharing.Response.UserResponse.CreateUserResponse;
import com.example.artworksharing.auth.AuthenticationRequest;
import com.example.artworksharing.auth.AuthenticationResponse;
import com.example.artworksharing.auth.AuthenticationService;
import com.example.artworksharing.auth.RegisterRequest;
import com.example.artworksharing.config.LogoutService;
import com.example.artworksharing.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final LogoutService logoutService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest authenticationRequest) {
        return ResponseEntity.ok(authenticationService.login(authenticationRequest));
    }

    @PostMapping("/refresh-token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        authenticationService.refreshToken(request,response);

    }

    @PostMapping("/createUser")
    public ResponseEntity<CreateUserResponse> register(@RequestBody CreatUserRequest request) throws IOException {
        return ResponseEntity.ok(authenticationService.createUser(request));
    }

//
//    @PostMapping("/logout")
//    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
//        logoutService.logout(request, response, null);
//        return ResponseEntity.ok("Logout successfully");
//    }



}
