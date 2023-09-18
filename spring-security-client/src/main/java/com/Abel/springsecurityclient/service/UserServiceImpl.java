package com.Abel.springsecurityclient.service;

import com.Abel.springsecurityclient.entity.User;
import com.Abel.springsecurityclient.entity.VerificationToken;
import com.Abel.springsecurityclient.model.UserModel;
import com.Abel.springsecurityclient.repository.UserRepository;
import com.Abel.springsecurityclient.repository.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private VerificationTokenRepository verificationTokenRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public User registerUser(UserModel userModel) {
        User user= new User();
        user.setEmail(userModel.getEmail());
        user.setFirstName(userModel.getFirstName());
        user.setLastName(userModel.getLastName());
        user.setRole("USER");
        user.setPassword(passwordEncoder.encode(userModel.getPassword()));
        userRepository.save(user);
        return user;
    }

    @Override
    public void saveVerificationTokenForUser(String token, User user) {
        VerificationToken verificationToken= new VerificationToken(user,token);
        verificationTokenRepository.save(verificationToken);
    }
}
