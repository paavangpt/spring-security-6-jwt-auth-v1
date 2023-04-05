package com.filoshare.app.events.listeners;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.filoshare.app.events.RegistrationCompleteEvent;
import com.filoshare.app.services.mail.EmailSenderService;
import com.filoshare.app.services.otp.OTPService;
import com.filoshare.app.utils.PrintFormatter;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RegistrationCompleteEventListener implements ApplicationListener<RegistrationCompleteEvent>{

    private final EmailSenderService emailSenderService;
    private final OTPService otpService;

    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {
        emailSenderService.sendEmail(event.getUser().getEmail(), "ContractSpan Email Verification OTP", "You OTP for verification is : " + otpService.generateOtp());
        new PrintFormatter().print("Oihoiii!!! New Event Got Published!!");
    }   
    
}
