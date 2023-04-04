package com.filoshare.app.configuration.security;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.catalina.connector.Response;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.filoshare.app.services.jwt.JwtService;

import ch.qos.logback.classic.Logger;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    public JwtService jwtService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        
        org.springframework.security.core.userdetails.User user = (User) authentication.getPrincipal();
        String token = jwtService.generateToken(user.getUsername());
        
        JSONObject jwtResponse = new JSONObject();

        List<String> roles = new ArrayList<>();
        user.getAuthorities().forEach(authority -> roles.add(authority.getAuthority()));

        jwtResponse.put("token", token);
        jwtResponse.put("email", user.getUsername());
        jwtResponse.put("roles", roles);

        PrintWriter out = response.getWriter();
        out.print(jwtResponse.toString());
        out.flush();
        
        // throw new UnsupportedOperationException("Unimplemented method 'onAuthenticationSuccess'");
    }

    
    
}
