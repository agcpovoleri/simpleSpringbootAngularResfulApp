package com.example.forumapp.dto;


import com.example.forumapp.entity.User;
import com.example.forumapp.entity.UserLogin;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@JsonAutoDetect(getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE)
public class AuthenticatedUserResponse implements Authentication {

    @JsonProperty
    private Long id;

    @JsonProperty
    private String login;

    @JsonProperty
    private List<String> groups;

    @JsonProperty
    private String authSessionKey;


    private AuthenticatedUserResponse() { }

    public static AuthenticatedUserResponse of(UserLogin user, String authSessionKey) {
        AuthenticatedUserResponse authenticatedUser = new AuthenticatedUserResponse();

        final User currentUser = user.getUser();

        authenticatedUser.setId(currentUser.getId());
        authenticatedUser.setLogin(user.getUsername());
        authenticatedUser.setAuthSessionKey(authSessionKey);
        authenticatedUser.setGroups(getGroupNamesFromUser());

        return authenticatedUser;
    }

    private static List<String> getGroupNamesFromUser(){
        return Lists.newArrayList("ROLE_USER");
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return this;
    }

    @Override
    public boolean isAuthenticated() {
        return true;
    }


    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException { }

    @Override
    public String getName() {
        return null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getAuthSessionKey() {
        return authSessionKey;
    }

    public void setAuthSessionKey(String authSessionKey) {
        this.authSessionKey = authSessionKey;
    }

    public List<String> getGroups() {
        if (groups == null) groups = Lists.newArrayList();
        return groups;
    }

    public void setGroups(List<String> groups) {
        this.groups = groups;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("login", login)
                .append("authSessionKey", authSessionKey)
                .append("roles", groups)
                .toString();
    }

}
