package com.Abel.springsecurityclient.service;

import com.Abel.springsecurityclient.entity.User;
import com.Abel.springsecurityclient.entity.VerificationToken;
import com.Abel.springsecurityclient.model.UserModel;

import java.util.Optional;

public interface UserService {
    User registerUser(UserModel userModel);

    void saveVerificationTokenForUser(String token, User user);

    String validateVerificationToken(String token);

    VerificationToken generateNewVerificationToken(String oldToken);

    User findUserByEmail(String email);

    void createpasswordResetTokenForUser(User user, String token);

    String validatePasswordToken();

    Optional<User> getUserByPasswordResetToken(String token);

    void changePassword(User user, String newPassword);

    boolean checkIfValidOldPassword(User user, String oldPassword);
}
