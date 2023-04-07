package com.filoshare.app.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.filoshare.app.events.RegistrationCompleteEvent;
import com.filoshare.app.models.tokens.VerificationTokens;
import com.filoshare.app.models.user.AuthenticationRequest;
import com.filoshare.app.models.user.AuthenticationResponse;
import com.filoshare.app.models.user.Role;
import com.filoshare.app.models.user.User;
import com.filoshare.app.repositories.tokens.VerificationTokensRepository;
import com.filoshare.app.repositories.user.RoleRepositoy;
import com.filoshare.app.repositories.user.UserRepository;
import com.filoshare.app.services.jwt.JwtService;
import com.filoshare.app.utils.PrintFormatter;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserController {

    public final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final RoleRepositoy roleRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final ApplicationEventPublisher eventPublisher;
    private final VerificationTokensRepository verificationTokensRepository;
    
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getUseremail(),
                request.getPassword()
            )
        );
        var user = userRepository.findByEmail(request.getUseremail()).orElseThrow();
        List<String> roles = new ArrayList<>(); 
        user.getAuthorities().forEach(authority -> roles.add(authority.getAuthority()));
        var jwtToken = jwtService.generateToken(user.getEmail());
        new PrintFormatter().print("Came in here ya know!");
        return ResponseEntity.ok(AuthenticationResponse.builder()
            .token(jwtToken)
            .email(user.getEmail())
            .roles(roles)
            .build());

    }

    @PostMapping("/sign-up")
    public ResponseEntity<AuthenticationResponse> signUp(@RequestBody User user, HttpServletRequest request) throws Exception{

        new PrintFormatter().print("Came in here sign up ya know!");

        if(user.equals(null)) {
            //TODO: make exception class
            throw new Exception("Please enter a valid email addess.");
        }
        if(userRepository.existsByEmail(user.getEmail())) {
            throw new Exception("User with email \"" + user.getEmail() + "\" already exists!");
        }

        List<String> roles = user.getRoles().stream()
            .map(roleName -> roleRepository.findByRole(roleName).getRole())
            .collect(Collectors.toList());

        user.setRoles(roles);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        String token = jwtService.generateToken(user.getEmail());
        AuthenticationResponse response = AuthenticationResponse.builder()
                                                                    .token(token)
                                                                    .email(user.getEmail())
                                                                    .roles(user.getRoles())
                                                                    .build();
        
        eventPublisher.publishEvent(new RegistrationCompleteEvent(user, getApplicationUrl(request), token));

        return ResponseEntity.ok(response);
    }

    // @PostMapping("/verify-registration")
    // public boolean verifyRegistration(@RequestBody VerificationTokens verificationTokens) {
    //     Optional<VerificationTokens> fetchedVerificationTokens = verificationTokensRepository.findByTokenAndOtpAndType(verificationTokens.getToken(), verificationTokens.getOtp(), verificationTokens.getType());
    //     if(fetchedVerificationTokens.get() != null) {

    //         return true;
    //     }
    //     return false;
    // }


    private String getApplicationUrl(HttpServletRequest request) {
        return "http://"
            + request.getServerName() + ":"
            + request.getServerPort()
            + request.getContextPath();
    }

}
