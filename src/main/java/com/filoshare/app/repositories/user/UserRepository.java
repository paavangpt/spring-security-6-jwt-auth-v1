package com.filoshare.app.repositories.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.filoshare.app.models.user.User;

public interface UserRepository extends JpaRepository<User, String> {
    
    Optional<User> findByEmail(String email);
    Boolean existsByEmail(String email);

}
