package com.filoshare.app.events.listeners;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.filoshare.app.events.RegistrationCompleteEvent;
import com.filoshare.app.models.tokens.VerificationTokens;
import com.filoshare.app.models.tokens.VeritificationType;
import com.filoshare.app.repositories.tokens.VerificationTokensRepository;
import com.filoshare.app.services.mail.EmailSenderService;
import com.filoshare.app.services.otp.OTPService;
import com.filoshare.app.utils.PrintFormatter;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RegistrationCompleteEventListener implements ApplicationListener<RegistrationCompleteEvent>{

    private final EmailSenderService emailSenderService;
    private final OTPService otpService;
    private final VerificationTokensRepository verificationTokensRepository;

    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {
        String otp = otpService.generateOtp();
        String userEmail = event.getUser().getEmail();

        VerificationTokens verificationTokens = new VerificationTokens(event.getToken(), otp, VeritificationType.REGISTRATION);
        verificationTokensRepository.save(verificationTokens);

        new PrintFormatter().print(userEmail);
        new PrintFormatter().print(otp);

        emailSenderService.sendEmail(userEmail, "Contract Span User Registration Verification OTP", "Your otp for verification is : " + otp);
    }   
    
}
