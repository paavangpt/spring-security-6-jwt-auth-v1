package com.filoshare.app.events;

import org.springframework.context.ApplicationEvent;

import com.filoshare.app.models.user.User;

import lombok.Data;

@Data
public class RegistrationCompleteEvent extends ApplicationEvent {

    private User user;
    private String applicationUrl;
    private String token;

    public RegistrationCompleteEvent(User user, String applicationUrl, String token) {
        super(user);
        this.user = user;
        this.applicationUrl = applicationUrl;
        this.token = token;
    }

}
