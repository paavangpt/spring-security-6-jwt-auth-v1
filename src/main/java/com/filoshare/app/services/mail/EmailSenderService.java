package com.filoshare.app.services.mail;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.filoshare.app.utils.PrintFormatter;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailSenderService {
 
    private final JavaMailSender mailSender;

    public void sendEmail(
        String to,
        String subject,
        String body
    ) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom("paavangpt@gmail.com");
        msg.setTo(to);
        msg.setSubject(subject);
        msg.setText(body);

        new PrintFormatter().print("Aur bro, ki haal chaal?");

        mailSender.send(msg);
    }


}
