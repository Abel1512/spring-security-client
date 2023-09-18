package com.Abel.springsecurityclient.event.listener;

import com.Abel.springsecurityclient.entity.User;
import com.Abel.springsecurityclient.event.RegistrationCompleteEvent;
import com.Abel.springsecurityclient.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;

import java.util.UUID;


@Slf4j
public class RegistrationCompleteEventListener implements ApplicationListener<RegistrationCompleteEvent> {
    @Autowired
    private UserService userService;

    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {
        User user =event.getUser();
        String token= UUID.randomUUID().toString();
        userService.saveVerificationTokenForUser(token,user);
        String url=event.getApplicationUrl()+"verifyRegistration?token=" + token;
        log.info("Click the link to verify your account :{}",url);
    }
}
