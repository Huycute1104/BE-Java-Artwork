package com.example.artworksharing.service;
import com.example.artworksharing.Request.UserRequest.SearchRequest;
import com.example.artworksharing.Request.UserRequest.UpdateUserRequest;
import com.example.artworksharing.Response.UserResponse.ChangeAvatarResponse;
import com.example.artworksharing.Response.UserResponse.ResponseUser;
import com.example.artworksharing.Response.UserResponse.UpdateUserResponse;
import com.example.artworksharing.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface UserService {
    public List<User> getAll();
    public UpdateUserResponse updateUser(String email, UpdateUserRequest updateUserRequest);
    public ChangeAvatarResponse changeAvatar(String email, MultipartFile file) throws IOException;
    public UpdateUserResponse banUser(String email);
    public UpdateUserResponse unbanUser(String email);
    public List<User> getCreator();
    ResponseEntity<ResponseUser> searchUsers(SearchRequest req);
}
