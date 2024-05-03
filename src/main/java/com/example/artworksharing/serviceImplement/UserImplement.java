package com.example.artworksharing.serviceImplement;


import com.example.artworksharing.enums.Role;
import com.example.artworksharing.model.User;
import com.example.artworksharing.repository.UserRepo;
import com.example.artworksharing.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserImplement implements UserService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public List<User> getAll() {
        List<User> allUsers = userRepo.findAll();
        return allUsers.stream()
                .filter(user -> user.getRole() == Role.AUDIENCE || user.getRole() == Role.CREATOR)
                .collect(Collectors.toList());
    }
}
