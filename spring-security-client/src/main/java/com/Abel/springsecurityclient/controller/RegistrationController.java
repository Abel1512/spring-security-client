package com.Abel.springsecurityclient.controller;

import com.Abel.springsecurityclient.entity.User;
import com.Abel.springsecurityclient.event.RegistrationCompleteEvent;
import com.Abel.springsecurityclient.model.UserModel;
import com.Abel.springsecurityclient.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegistrationController {
    @Autowired
    private UserService userService;
    @Autowired
    private ApplicationEventPublisher publisher;
   @PostMapping("/register")
    public String registerUser(@RequestBody UserModel userModel, final HttpServletRequest request){
        User user=userService.registerUser(userModel);
        publisher.publishEvent(new RegistrationCompleteEvent(user,aaplicationUrl(request)));

        return "SUCCESS";

    }

    private String aaplicationUrl(HttpServletRequest request) {
        return "http://"+
                request.getServerName()+
                ":"+
                request.getServerPort()+
                request.getContextPath();
    }
}
