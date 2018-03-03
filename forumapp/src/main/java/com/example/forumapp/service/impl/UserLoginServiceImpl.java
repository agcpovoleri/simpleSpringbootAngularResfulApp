package com.example.forumapp.service.impl;

import com.example.forumapp.dto.AuthenticatedUserResponse;
import com.example.forumapp.entity.UserLogin;
import com.example.forumapp.exception.UserNotActiveException;
import com.example.forumapp.exception.UserNotFoundException;
import com.example.forumapp.exception.UserNotLoggedException;
import com.example.forumapp.repository.UserLoginRepository;
import com.example.forumapp.service.RedisHelper;
import com.example.forumapp.service.UserLoginService;
import com.example.forumapp.service.UserService;
import com.example.forumapp.util.SecurityUtils;
import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@Transactional(readOnly = true)
public class UserLoginServiceImpl implements UserLoginService {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    public static final int TEN_UNITS = 10;

    @Autowired
	private UserLoginRepository userLoginRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private RedisHelper redisHelper;

    @Override
	public UserLogin findByUsername(String username) {
		return userLoginRepository.findByUsername(username);
	}

//
//    @Override
//    public User findUserByAuthToken(String authToken) {
//        AuthenticatedUserResponse authenticatedUser = this.getAuthenticatedUserFromToken(authToken);
//        return userService.findOne(authenticatedUser.getId());
//    }

    @Override
    public UserLogin findOne(Long id) {
        return userLoginRepository.findOne(id);
    }

    @Transactional(readOnly = false)
    public AuthenticatedUserResponse login(String login, String hash){

        UserLogin userLogin = Optional.ofNullable(this.userLoginRepository.findByUsername(login)).orElseThrow(UserNotFoundException::new);

        userLogin.authenticate(hash);

        return this.createAuthSession(userLogin);
    }

    @Override
    public void logout(String authToken) {
        redisHelper.deleteKeyFromToken(authToken);
    }

    @Override
    public AuthenticatedUserResponse getAuthenticatedUserFromToken(String token) throws UserNotLoggedException {
        return redisHelper.getAuthenticatedUserFromToken(token);
    }

    @Override
    public AuthenticatedUserResponse refreshToken(String authToken) {

        Preconditions.checkNotNull(authToken);

        AuthenticatedUserResponse authenticatedUser = redisHelper.getAuthenticatedUserFromToken(authToken);
        redisHelper.setAuthTokenIntoRedis(redisHelper.getActiveSessionKeyFor(authToken), authenticatedUser);

        return authenticatedUser;
    }
    private AuthenticatedUserResponse createAuthSession(UserLogin userLogin) {

        String authToken = SecurityUtils.getUUID();
        String authSessionKey = redisHelper.getActiveSessionKeyFor(authToken);

        AuthenticatedUserResponse authenticatedUser = AuthenticatedUserResponse.of(userLogin, authToken);

        redisHelper.setAuthTokenIntoRedis(authSessionKey, authenticatedUser);

        return authenticatedUser;
    }


}
