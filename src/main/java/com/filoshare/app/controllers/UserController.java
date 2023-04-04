package com.filoshare.app.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.filoshare.app.models.user.Role;
import com.filoshare.app.models.user.User;
import com.filoshare.app.repositories.user.RoleRepositoy;
import com.filoshare.app.repositories.user.UserRepository;
import com.filoshare.app.services.jwt.JwtService;

@RestController
public class UserController {

    @Autowired
    public UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepositoy roleRepository;

    @Autowired
    private JwtService jwtService;
    
    @PostMapping("/sign-up")
    public ResponseEntity<String> signUp(@RequestBody User user){

        System.out.println("Came in here!");

        if(user.equals(null)) {
            return ResponseEntity.badRequest().body("Please enter valid user details");
        }
        if(userRepository.existsByEmail(user.getEmail())) {
            return ResponseEntity.badRequest().body("User with email \"" + user.getEmail() + "\" already exists!");
        }

        List<String> roles = user.getRoles().stream()
            .map(roleName -> roleRepository.findByRole(roleName).getRole())
            .collect(Collectors.toList());

        user.setRoles(roles);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        return ResponseEntity.ok("User Created Successfully");
    }

}
