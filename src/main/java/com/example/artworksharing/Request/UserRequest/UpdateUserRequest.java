package com.example.artworksharing.Request.UserRequest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserRequest {
    private String name;
//    private String email;
//    private byte[] avatar;
    private String password;
    private String phone;
//    private boolean status;
//    private Role role;
}
