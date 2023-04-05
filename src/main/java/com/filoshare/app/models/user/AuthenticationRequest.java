package com.filoshare.app.models.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthenticationRequest {
    private String useremail;
    private String password;
}
