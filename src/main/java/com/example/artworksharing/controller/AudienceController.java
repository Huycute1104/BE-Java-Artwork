package com.example.artworksharing.controller;


import com.example.artworksharing.Request.UserRequest.UpdateUserRequest;
import com.example.artworksharing.Response.UserResponse.ChangeAvatarResponse;
import com.example.artworksharing.Response.UserResponse.UpdateUserResponse;
import com.example.artworksharing.model.User;
import com.example.artworksharing.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/user")
@PreAuthorize("hasRole('AUDIENCE')")
public class AudienceController {

    @Autowired
    private UserService iUserService;

    @GetMapping("/list")
//    @PreAuthorize("hasAuthority('audience:read')")
    @PreAuthorize("hasAnyAuthority('audience:read','creator:read')")
    public List<User> getAllUser() {
        return iUserService.getAll();
    }

    @PutMapping("/updateUser/{email}")
    @PreAuthorize("hasAuthority('audience:update')")
    public ResponseEntity<UpdateUserResponse> updateUser(
            @PathVariable String email,
            @RequestBody UpdateUserRequest updateUserRequest) {
//        UpdateUserResponse response = iUserService.updateUser(email, updateUserRequest);
        return ResponseEntity.ok(iUserService.updateUser(email, updateUserRequest));
    }
    @PostMapping("/avatar/{email}")
    @PreAuthorize("hasAuthority('audience:create')")
    public ResponseEntity<ChangeAvatarResponse> changeAvatar(
            @PathVariable String email,
            @RequestParam("image") MultipartFile file) throws IOException {
        ChangeAvatarResponse changeAvatar = iUserService.changeAvatar(email, file);
        return ResponseEntity.ok(changeAvatar);
    }


}
