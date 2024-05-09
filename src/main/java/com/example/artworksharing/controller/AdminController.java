package com.example.artworksharing.controller;

import com.example.artworksharing.Request.UserRequest.SearchRequest;
import com.example.artworksharing.Response.UserResponse.ResponseUser;
import com.example.artworksharing.Response.UserResponse.UpdateUserResponse;
import com.example.artworksharing.model.User;
import com.example.artworksharing.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    @Autowired
    private UserService iUserService;

    @PutMapping("/ban/{email}")
    @PreAuthorize("hasAuthority('admin:update')")
    public ResponseEntity<UpdateUserResponse> banUser(@PathVariable String email) {
        UpdateUserResponse response = iUserService.banUser(email);
        if (response.getUser() != null) {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
    @PutMapping("/unban/{email}")
    @PreAuthorize("hasAuthority('admin:update')")
    public ResponseEntity<UpdateUserResponse> unbanUser(@PathVariable String email) {
        UpdateUserResponse response = iUserService.unbanUser(email);
        if (response.getUser() != null) {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
    @GetMapping("/creators")
    @PreAuthorize("hasAuthority('admin:read')")
    public List<User> getCreators() {
        return iUserService.getCreator();
    }

    @GetMapping("/searchUser")
    @PreAuthorize("hasAuthority('admin:read')")
    public ResponseEntity<ResponseUser> getAllUsers(@RequestBody SearchRequest req) {
        return iUserService.searchUsers(req);
    }
}







