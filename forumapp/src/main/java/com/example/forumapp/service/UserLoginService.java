package com.example.forumapp.service;

import com.example.forumapp.dto.AuthenticatedUserResponse;
import com.example.forumapp.entity.User;
import com.example.forumapp.entity.UserLogin;
import com.example.forumapp.exception.UserNotLoggedException;

public interface UserLoginService {

    UserLogin findByUsername(String username);
//    User findUserByAuthToken(String authToken) throws UserNotLoggedException;
    UserLogin findOne(Long id);
    void logout(String authSessionToken);
//
    AuthenticatedUserResponse getAuthenticatedUserFromToken(String token) throws UserNotLoggedException;
    AuthenticatedUserResponse login(String login, String hash);
////    AuthenticatedUser loginFromFacebook(String oauthCode);
////
    AuthenticatedUserResponse refreshToken(String authSessionToken);
//    int countActiveUserSessions();
//
//    void validate(UserLogin userLogin);
//
//    void updateFieldsToRecoverPassword(UserLogin userLogin);
//
//    void updateUsersPassword(UserLogin userLogin, String newPassword);

}
