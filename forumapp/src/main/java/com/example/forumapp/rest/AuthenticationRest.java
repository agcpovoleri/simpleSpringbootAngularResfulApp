package com.example.forumapp.rest;

import com.example.forumapp.config.filter.StatelessAuthenticationFilter;
import com.example.forumapp.dto.AuthenticatedUserResponse;
import com.example.forumapp.dto.LoginRequest;
import com.example.forumapp.exception.UserNotLoggedException;
import com.example.forumapp.service.UserLoginService;
import com.example.forumapp.service.UserService;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.ws.rs.core.MediaType;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationRest {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    UserLoginService userLoginService;

    @Autowired
    UserService userService;

    @RequestMapping(
            value = "/login",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
    public AuthenticatedUserResponse login(@Valid @RequestBody LoginRequest loginRequest,
                                           @RequestHeader(value = StatelessAuthenticationFilter.TOKEN_KEY, required = false) String authKeyToken) {
        AuthenticatedUserResponse authenticatedUser;
        if (!Strings.isNullOrEmpty(authKeyToken)){
            try {
                authenticatedUser = userLoginService.refreshToken(authKeyToken);
            } catch (UserNotLoggedException e) {
                authenticatedUser = userLoginService.login(loginRequest.getLogin(), loginRequest.getPassword());
            }
        } else {
            authenticatedUser = userLoginService.login(loginRequest.getLogin(), loginRequest.getPassword());
        }

        logger.debug(String.format("Logging user: %s", authenticatedUser.getName()));
        return authenticatedUser;
    }

    @ResponseStatus(value = HttpStatus.OK)
    @RequestMapping(value = "/refresh", method = RequestMethod.POST)
    public void refreshToken(@Valid @RequestHeader(value = StatelessAuthenticationFilter.TOKEN_KEY, required = true) String authKeyToken)
    {
        userLoginService.refreshToken(authKeyToken);
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public void logout(@Valid @RequestHeader(value = StatelessAuthenticationFilter.TOKEN_KEY, required = true) String authKeyToken)
    {
        userLoginService.logout(authKeyToken);
    }
}
