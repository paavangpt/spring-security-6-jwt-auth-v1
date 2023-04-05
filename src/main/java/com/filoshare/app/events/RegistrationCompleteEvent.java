package com.filoshare.app.events;

import org.springframework.context.ApplicationEvent;

import com.filoshare.app.models.user.User;

import lombok.Data;

@Data
public class RegistrationCompleteEvent extends ApplicationEvent {

    private User user;
    private String applicationUrl;

    public RegistrationCompleteEvent(User user, String applicationUrl) {
        super(user);
        this.user = user;
        this.applicationUrl = applicationUrl;
    }

}
