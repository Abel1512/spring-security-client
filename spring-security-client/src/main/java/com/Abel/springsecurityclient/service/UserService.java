package com.Abel.springsecurityclient.service;

import com.Abel.springsecurityclient.entity.User;
import com.Abel.springsecurityclient.model.UserModel;

public interface UserService {
    User registerUser(UserModel userModel);

    void saveVerificationTokenForUser(String token, User user);
}
