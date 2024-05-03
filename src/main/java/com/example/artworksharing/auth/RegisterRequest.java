package com.example.artworksharing.auth;

import com.example.artworksharing.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String name;
    private String email;
//    private String avatar;
    private byte[] avatar;
    private String password;
    private String phone;
    private boolean status;
    private Role role;
    private Double account_balance;


}
