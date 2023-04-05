package com.filoshare.app.models.user;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthenticationResponse {

    private String token;
    private String email;
    private List<String> roles;
    
}
