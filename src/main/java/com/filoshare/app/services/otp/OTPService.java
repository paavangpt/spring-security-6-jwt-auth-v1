package com.filoshare.app.services.otp;

import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

@Service
public class OTPService {
    
    public String generateOtp() {
        Random random = new Random();
        int numDigits = 6;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < numDigits; i++) {
            sb.append(random.nextInt(10));
        }
        String otp = sb.toString();
        return otp;
    }

}
