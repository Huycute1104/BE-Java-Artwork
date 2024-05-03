package com.example.artworksharing.auth;

import com.example.artworksharing.Request.UserRequest.CreatUserRequest;
import com.example.artworksharing.Response.UserResponse.CreateUserResponse;
import com.example.artworksharing.config.JwtService;
import com.example.artworksharing.enums.Role;
import com.example.artworksharing.enums.TokenType;
import com.example.artworksharing.model.Token;
import com.example.artworksharing.model.User;
import com.example.artworksharing.repository.TokeRepo;
import com.example.artworksharing.repository.UserRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

import static com.example.artworksharing.enums.Role.AUDIENCE;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepo userRepo;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final TokeRepo tokeRepo;

    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder()
                .email(request.getEmail())
                .accountName(request.getName())
                .avatar(request.getAvatar())
                .password(passwordEncoder.encode(request.getPassword()))
                .phone(request.getPhone())
                .userStatus(request.isStatus())
                .role(request.getRole())
                .build();
        var save = userRepo.save(user);
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        saveUserToken(save, jwtToken);
        return AuthenticationResponse.builder()
                .status("You have successfully registered.")
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    public CreateUserResponse createUser(CreatUserRequest request) throws IOException {
        String email = request.getEmail();
        String role = request.getUserRole();
        Optional<User> existingUser = userRepo.findUserByEmail(email);
        if (existingUser.isPresent()) {
            return CreateUserResponse.builder()
                    .status("Email already exists")
                    .user(null)
                    .build();
        }else{
            if (role.equalsIgnoreCase("Audience")) {
                var register = User.builder()
                        .email(request.getEmail())
                        .accountName(request.getName())
//                .avatar(request.getAvatar())
                        .password(passwordEncoder.encode(request.getPassword()))
                        .phone(request.getPhone())
                        .userStatus(true)
                        .role(AUDIENCE)
                        .build();
                userRepo.save(register);
                return CreateUserResponse.builder()
                        .status("You have successfully registered with role"+ AUDIENCE)
                        .user(register)
                        .build();

            } else if(role.equalsIgnoreCase("Creator")) {
                var register = User.builder()
                        .email(request.getEmail())
                        .accountName(request.getName())
//                .avatar(request.getAvatar())
                        .password(passwordEncoder.encode(request.getPassword()))
                        .phone(request.getPhone())
                        .userStatus(true)
                        .role(Role.CREATOR)
                        .build();
                userRepo.save(register);
                return CreateUserResponse.builder()
                        .status("You have successfully registered with role"+Role.CREATOR)
                        .user(register)
                        .build();
            }else{
                return CreateUserResponse.builder()
                        .status("This role not found")
                        .user(null)
                        .build();
            }
        }
    }

    private void revokeAllUserTokens(User user) {
        var validToken = tokeRepo.findAllValidTokensByUser(user.getUsersID());
        if (validToken.isEmpty())
            return;
        validToken.forEach(t -> {
            t.setRevoked(true);
            t.setExpired(true);
        });
        tokeRepo.saveAll(validToken);
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .revoked(false)
                .expired(false)
                .build();
        tokeRepo.save(token);
    }

    public AuthenticationResponse login(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = userRepo.findByEmail(request.getEmail()).orElseThrow();
        if(!user.isUserStatus()){
            return AuthenticationResponse.builder()
                    .status("User is ban")
                    .build();
        }else{
            var jwtToken = jwtService.generateToken(user);
            var refreshToken = jwtService.generateRefreshToken(user);
            revokeAllUserTokens(user);
            saveUserToken(user, jwtToken);
            return AuthenticationResponse.builder()
                    .status("Login successfully")
//                .userInfo(userRepo.findUserByEmail(request.getEmail()).orElseThrow())
                    .accessToken(jwtToken)
                    .refreshToken(refreshToken)
                    .userInfo(user)
                    .build();
        }

    }

    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String jwt;
        final String userEmail;
        if (authHeader == null || !authHeader.startsWith("Bearer ")){
            return;
        }
        refreshToken =authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);// todo extract the userEmail from JWT Token
        if(userEmail!= null ){
            var user = this.userRepo.findUserByEmail(userEmail).orElseThrow();
            if(jwtService.isTokenValid(refreshToken,user)){
               var accessToken = jwtService.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
               var authResponse = AuthenticationResponse.builder()
                       .accessToken(accessToken)
                       .refreshToken(refreshToken)
                       .build();
               new ObjectMapper().writeValue(response.getOutputStream(),authResponse);
            }
        }
    }
}
