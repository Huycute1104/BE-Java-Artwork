package com.example.artworksharing.service;
import com.example.artworksharing.Request.UserRequest.UpdateUserRequest;
import com.example.artworksharing.Response.UserResponse.UpdateUserResponse;
import com.example.artworksharing.model.User;

import java.util.List;

public interface UserService {
    public List<User> getAll();
    public UpdateUserResponse updateUser(String email, UpdateUserRequest updateUserRequest);
}
