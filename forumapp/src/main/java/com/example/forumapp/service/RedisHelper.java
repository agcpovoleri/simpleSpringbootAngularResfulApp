package com.example.forumapp.service;

import com.example.forumapp.dto.AuthenticatedUserResponse;
import com.example.forumapp.exception.UserNotLoggedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class RedisHelper {

    public static final int FIVE_UNITS = 5;

    @Autowired
    private RedisTemplate<String, AuthenticatedUserResponse> redis;

    public void deleteKeyFromToken(String authToken) {
        this.redis.delete(this.getActiveSessionKeyFor(authToken));
    }

    public AuthenticatedUserResponse getAuthenticatedUserFromToken(String token) throws UserNotLoggedException {
        return Optional.ofNullable(this.getAuthenticatedUserFromRedis(token)).orElseThrow(UserNotLoggedException::new);
    }

    public AuthenticatedUserResponse getAuthenticatedUserFromRedis(String token) {

        BoundValueOperations<String, AuthenticatedUserResponse> ops = this.redis.boundValueOps(this.getActiveSessionKeyFor(token));
        return Optional.ofNullable(ops.get()).orElse(null);
    }
    public String getActiveSessionKeyFor(String uuid) {
        return String.format("user:%s", uuid);
    }

    public int countActiveUserSessions() {
        return this.redis.keys(this.getActiveSessionKeyFor("*")).size();
    }

    public void setAuthTokenIntoRedis(String authSessionKey, AuthenticatedUserResponse authenticatedUser) {

        BoundValueOperations<String, AuthenticatedUserResponse> ops = this.redis.boundValueOps(authSessionKey);
        ops.setIfAbsent(authenticatedUser);
        ops.expire(FIVE_UNITS, TimeUnit.MINUTES);
    }
}
