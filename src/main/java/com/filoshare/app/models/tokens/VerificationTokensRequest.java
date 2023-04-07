package com.filoshare.app.models.tokens;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VerificationTokensRequest {
    
    private String otp;
    private String token;

}
