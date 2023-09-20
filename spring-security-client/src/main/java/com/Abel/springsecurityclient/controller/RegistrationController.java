package com.Abel.springsecurityclient.controller;

import com.Abel.springsecurityclient.entity.User;
import com.Abel.springsecurityclient.entity.VerificationToken;
import com.Abel.springsecurityclient.event.RegistrationCompleteEvent;
import com.Abel.springsecurityclient.model.PasswordModel;
import com.Abel.springsecurityclient.model.UserModel;
import com.Abel.springsecurityclient.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@Slf4j
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
    @GetMapping("/verifyRegistration")
    public String verifyRegistration(@RequestParam("token") String token){
       String result= userService.validateVerificationToken(token);
       if(result.equalsIgnoreCase("valid")){
           return "User Verifies Successfully";
       }
       return "Bad User";
    }
    @GetMapping("/resendVerifyToken")
    public String resendVerificationToken(@RequestParam("token")String oldToken,HttpServletRequest request){
        VerificationToken verificationToken
                = userService.generateNewVerificationToken(oldToken);
        User user=verificationToken.getUser();
        resendVerificationTokenMail(user,aaplicationUrl(request),verificationToken);
        return "verificatio Link Sent";
    }
    @PostMapping("/resetPassword")
    public String resetPassword(@RequestBody PasswordModel passwordModel){
       User user=userService.findUserByEmail(passwordModel.getEmail());
       String url ="";
       if(user!=null){
           String token= UUID.randomUUID().toString();
           userService.createpasswordResetTokenForUser(user,token);
           HttpServletRequest request = null;
           url=passwordResetTokenMail(user,
                   aaplicationUrl(request),
                   token);
       }
       return url;
    }
    @PostMapping("/savePassword")
    public String savePassword(@RequestParam("token") String token,@RequestBody PasswordModel passwordModel){
       String result = userService.validatePasswordToken();
       if(!result.equalsIgnoreCase("valid")){
           return "Invalid Token";
       }
        Optional<User> user= userService.getUserByPasswordResetToken(token);
       if(user.isPresent()){
           userService.changePassword(user.get(),passwordModel.getNewPassword());

           return "Password reset Successfully";

       }else{
           return "Invalid Token";
        }
    }
    @PostMapping("/changePassword")
    public String changePassword(@RequestBody PasswordModel passwordModel){
       User user =userService.findUserByEmail(passwordModel.getEmail());
       if(!userService.checkIfValidOldPassword(user,passwordModel.getOldPassword())){
           return "Invalid Old Password";
       }
       userService.changePassword(user,passwordModel.getNewPassword());
       return "Password Changed Successfully";

   }
    private String passwordResetTokenMail(User user,String applicationUrl,String token){
        String url=applicationUrl+"savePassword?token=" + token;
        log.info("Click the link to reset your password :{}",url);
        return url;
    }

    private void resendVerificationTokenMail(User user, String applicationUrl, VerificationToken verificationToken) {
        String url=applicationUrl+"verifyRegistration?token=" + verificationToken.getToken();
        log.info("Click the link to verify your account :{}",url);
    }



    private String aaplicationUrl(HttpServletRequest request) {
        return "http://"+
                request.getServerName()+
                ":"+
                request.getServerPort()+
                request.getContextPath();
    }
}
