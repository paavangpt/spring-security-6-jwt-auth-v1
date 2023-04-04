package com.filoshare.app.configuration.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.filoshare.app.services.jwt.JwtService;

import io.micrometer.common.lang.NonNull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private JwtService jwtService;

    public JwtAuthenticationFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(
        @NonNull HttpServletRequest request,
        @NonNull HttpServletResponse response,
        @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        if(
            request.getRequestURL().equals("/login") ||
            request.getRequestURL().equals("/log-in") ||
            request.getRequestURL().equals("/sign-up") ||
            request.getRequestURL().equals("/public")
        ) {
            filterChain.doFilter(request, response);
        }

        String authHeader = request.getHeader("Authorization");
        String token = "";
        String[] splitted = authHeader.split(" ");

        String userEmail;

        if(splitted.length < 2) {
            System.out.println("Un-Authorized!");
            doFilter(request, response, filterChain);
        } else {
            token = splitted[1];
            userEmail = jwtService.extractUseremail(token);
            System.out.println("Email is : " + userEmail);
        }


        System.out.println("The request got filtered!");
        filterChain.doFilter(request, response);

    }
    
    
}
