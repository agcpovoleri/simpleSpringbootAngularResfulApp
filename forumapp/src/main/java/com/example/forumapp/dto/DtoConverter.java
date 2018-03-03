package com.example.forumapp.dto;


import com.example.forumapp.entity.User;
import com.example.forumapp.entity.UserLogin;
import org.apache.commons.lang3.StringUtils;

public class DtoConverter {

    public static User buildUserFromCreateRequest(UserCreateRequest request) {

        final User newUser = new User();
        newUser.setName(request.getFirstName() + " " + request.getLastName());
        newUser.setEmail(request.getEmail());

        final UserLogin userLogin = new UserLogin();
        userLogin.setUsername(request.getUsername());
        userLogin.setPassword(request.getPassword());
        userLogin.setUser(newUser);

        newUser.setUserLogin(userLogin);
        return newUser;
    }

    public static User buildUserFromUpdateRequest(UserUpdateRequest request) {

        final User newUser = new User();
        newUser.setId(request.getId());
        newUser.setName(request.getName());
        newUser.setEmail(request.getEmail());

        final UserLogin userLogin = new UserLogin();
        userLogin.setUsername(request.getUsername());
        userLogin.setPassword(request.getPassword());
        userLogin.setUser(newUser);

        newUser.setUserLogin(userLogin);
        return newUser;
    }

    public static UserDetailResponse buildUserDetail(User user) {

        final UserDetailResponse userDetail = new UserDetailResponse();
        userDetail.setId(user.getId());
        userDetail.setName(user.getName());
        userDetail.setEmail(user.getEmail());
        userDetail.setUsername(user.getUserLogin().getUsername());

        return userDetail;
    }
}
