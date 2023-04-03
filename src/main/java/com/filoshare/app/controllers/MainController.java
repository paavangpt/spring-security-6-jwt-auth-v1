package com.filoshare.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.filoshare.app.models.user.MyUserDetails;

import io.jsonwebtoken.Jwts;

@RestController
public class MainController {

    @GetMapping("/admin")
    public String adminPage() {
        return "Welcome Captain! 🙋‍♂️";
    }

    @GetMapping("/")
    public String homePage() {
        return "Welcome to the home page dude!";
    }


    @GetMapping("/public")
    public String publicPage() {
        return "Hey There 👋 Welcome to the public page!";
    }

}
