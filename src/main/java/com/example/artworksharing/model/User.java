package com.example.artworksharing.model;

import com.example.artworksharing.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "User")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UsersID")
    private int usersID;

    @Column(name = "AccountName", length = 20)
    private String accountName;

    @Column(name = "Email", length = 50)
    private String email;

//    @Column(name = "Avatar", length = 50)
//    private String avatar;

    @Lob
    @Column(name = "Avatar", length = 1000)
    private byte[] avatar;

    @Column(name = "Password", length = 100)
    private String password;

    @Column(name = "Phone", length = 50)
    private String phone;

    @Column(name = "UserStatus")
    private boolean userStatus;

    @Column(name = "Account_balance")
    private double accountBalance;



    @Enumerated(EnumType.STRING)
    private Role role;

    @JsonIgnore
    @OneToMany(mappedBy ="user")
    private List<Token> tokens;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
       return role.getAuthorities();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
