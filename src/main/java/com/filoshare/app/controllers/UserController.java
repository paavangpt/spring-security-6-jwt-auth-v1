package com.filoshare.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.filoshare.app.models.user.User;
import com.filoshare.app.repositories.user.UserRepository;

@RestController
public class UserController {

    @Autowired
    public UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    
    @PostMapping("/sign-up")
    public ResponseEntity<String> signUp(@RequestBody User user){

        System.out.println("Came in here!");

        if(user.equals(null)) {
            return ResponseEntity.badRequest().body("Please enter valid user details");
        }
        if(userRepository.existsByEmail(user.getEmail())) {
            return ResponseEntity.badRequest().body("User with email \"" + user.getEmail() + "\" already exists!");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        return ResponseEntity.ok("User Created Successfully");
    }

}
