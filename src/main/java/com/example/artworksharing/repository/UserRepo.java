package com.example.artworksharing.repository;


import com.example.artworksharing.enums.Role;
import com.example.artworksharing.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface
UserRepo extends JpaRepository<User,Integer> {

    Optional<User> findByEmail(String email);

    Optional<User> findUserByEmail(String email);

    List<User> findByRole(Role role);

    Optional<User> findUserByUsersID(int userid);

    Optional<User> findByUsersID(Integer userid);

    @Query("SELECT u FROM User u WHERE " +
            "(:searchValue IS NULL OR LOWER(u.accountName) LIKE %:searchValue% OR LOWER(u.email) LIKE %:searchValue%) AND " +
            "(:phone IS NULL OR u.phone = :phone)")
    List<User> findUsersByFilter(String searchValue, String phone);



}
