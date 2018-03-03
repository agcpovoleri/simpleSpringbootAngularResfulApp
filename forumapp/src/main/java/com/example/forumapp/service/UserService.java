package com.example.forumapp.service;

import com.example.forumapp.entity.User;
import com.example.forumapp.exception.UserAlreadyExistException;
import com.example.forumapp.exception.UserEmailAlreadyUsedException;

import java.util.List;

public interface UserService {

    User findOne(Long id);
    User findByEmail(String email);

    List<User> findAll();
    void createUser(User user) throws UserAlreadyExistException, UserEmailAlreadyUsedException;
    void update(User user);
	void delete(Long id);
}
