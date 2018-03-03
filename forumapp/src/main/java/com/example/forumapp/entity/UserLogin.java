package com.example.forumapp.entity;

import com.example.forumapp.exception.InvalidPasswordException;
import com.example.forumapp.util.SecurityUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.Preconditions;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "USER_LOGIN")
public class UserLogin implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;

    @JsonIgnore
    private String password;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL, optional = false, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name="USER_ID", nullable=false)
    private User user;

    public void authenticate(String rawPassword) throws InvalidPasswordException {

        Preconditions.checkArgument(rawPassword != null, "login.request.authentication.password.not.null");

        if (!this.password.equals(SecurityUtils.generateMd5(rawPassword))) {
            throw new InvalidPasswordException();
        }
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
