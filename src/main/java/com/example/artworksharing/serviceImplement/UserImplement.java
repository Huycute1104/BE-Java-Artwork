package com.example.artworksharing.serviceImplement;


import com.example.artworksharing.Request.UserRequest.UpdateUserRequest;
import com.example.artworksharing.Response.UserResponse.ChangeAvatarResponse;
import com.example.artworksharing.Response.UserResponse.UpdateUserResponse;
import com.example.artworksharing.Util.ImageUtil;
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

    @Override
    public UpdateUserResponse updateUser(String email, UpdateUserRequest updateUserRequest) {
        var existedUser = userRepo.findUserByEmail(email).orElse(null);
        if (existedUser != null) {
            existedUser.setAccountName(updateUserRequest.getName());
            existedUser.setPassword(passwordEncoder.encode(updateUserRequest.getPassword()));
            existedUser.setPhone(updateUserRequest.getPhone());
            userRepo.save(existedUser);
            return UpdateUserResponse.builder()
                    .status("Update User Successful")
                    .user(existedUser)
                    .build();
        } else {
            return UpdateUserResponse.builder()
                    .status("User Not Found")
                    .user(null)
                    .build();

        }
    }

    @Override
    public ChangeAvatarResponse changeAvatar(String email, MultipartFile file) throws IOException {
        var user = userRepo.findUserByEmail(email).orElse(null);
        if (user != null) {
            user.setAvatar(ImageUtil.compressImage(file.getBytes()));
            userRepo.save(user);
            return ChangeAvatarResponse.builder()
                    .status("Update Avatar Successful")
                    .user(getUserInfo(email))
                    .build();
        } else {
            return ChangeAvatarResponse.builder()
                    .status("User Not Found")
                    .user(null)
                    .build();
        }
    }

    @Override
    public UpdateUserResponse banUser(String email) {
        var banUser = userRepo.findUserByEmail(email).orElse(null);
//        var banUser = userRepo.findUserByUsersID(userID).orElse(null);
        if (banUser != null) {
            banUser.setUserStatus(false);
            userRepo.save(banUser);
            return UpdateUserResponse.builder()
                    .status("Ban User Successful")
                    .user(banUser)
                    .build();
        } else {
            return UpdateUserResponse.builder()
                    .status("User Not Found")
                    .user(null)
                    .build();

        }
    }

    @Override
    public UpdateUserResponse unbanUser(String email) {
        var unbanUser = userRepo.findUserByEmail(email).orElse(null);
//        var unbanUser = userRepo.findUserByUsersID(userID).orElse(null);
        if (unbanUser != null) {
            unbanUser.setUserStatus(true);
            userRepo.save(unbanUser);
            return UpdateUserResponse.builder()
                    .status("UnBan User Successful")
                    .user(unbanUser)
                    .build();
        } else {
            return UpdateUserResponse.builder()
                    .status("User Not Found")
                    .user(null)
                    .build();

        }
    }

    @Override
    public List<User> getCreator() {
        Role creatorRole = Role.CREATOR;
        return userRepo.findByRole(creatorRole);
    }

    public User getUserInfo(String email) {
        User user = userRepo.findUserByEmail(email).orElse(null);
        if (user != null) {
            byte[] compressedImage = user.getAvatar();
            if (compressedImage != null && compressedImage.length > 0) {
                byte[] decompressedImage = ImageUtil.decompressImage(compressedImage);
                user.setAvatar(decompressedImage);
            }
            return user;
        }
        return null;
    }

}
