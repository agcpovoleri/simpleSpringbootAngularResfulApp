package com.example.forumapp.service.impl;

import com.example.forumapp.entity.User;
import com.example.forumapp.entity.UserLogin;
import com.example.forumapp.exception.*;
import com.example.forumapp.repository.UserLoginRepository;
import com.example.forumapp.repository.UserRepository;
import com.example.forumapp.service.UserService;
import com.example.forumapp.util.SecurityUtils;
import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    @Autowired
	private UserRepository userRepository;
    @Autowired
    private UserLoginRepository userLoginRepository;

    @Override
	public User findOne(Long id) {
		return userRepository.findOne(id);
	}

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public List<User> findAll() {
		return (List<User>) userRepository.findAll();
	}

	@Override
	@Transactional(readOnly = false)
	public void createUser(User user) throws UserAlreadyExistException, UserEmailAlreadyUsedException {

        UserLogin ul = user.getUserLogin();
        Optional optionalUser = Optional.ofNullable(userRepository.findByEmail(user.getEmail()));
        if (optionalUser.isPresent()) throw new UserEmailAlreadyUsedException();

        Optional anotherUserLogin =   Optional.ofNullable(userLoginRepository.findByUsername(ul.getUsername()));
        if (anotherUserLogin.isPresent()) throw new UsernameAlreadyUsedException();

        ul.setPassword(SecurityUtils.generateMd5(ul.getPassword()));
        ul.setUser(user);
        userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = false)
    public void update(User userToPersist) {

        //validate user id
        User originalUser = userRepository.findOne(userToPersist.getId());
        Optional.of(originalUser).orElseThrow(UserNotFoundException::new);

        //validate email change
        if (!originalUser.getEmail().equals(userToPersist.getEmail())) {
            throw new UserEmailCannotBeChangedException();
        }

        if (!originalUser.getUserLogin().getUsername().equals(userToPersist.getUserLogin().getUsername())) {
            UserLogin originalUserLogin = userLoginRepository.findByUsername(userToPersist.getUserLogin().getUsername());
            Optional.of(originalUserLogin).orElseThrow(UsernameAlreadyUsedException::new);
        }

        originalUser.setName(userToPersist.getName());
        originalUser.setEmail(userToPersist.getEmail());
        originalUser.getUserLogin().setUsername(userToPersist.getUserLogin().getUsername());
        originalUser.getUserLogin().setPassword(SecurityUtils.generateMd5(userToPersist.getUserLogin().getPassword()));

        userRepository.save(originalUser);
    }


    @Override
	@Transactional(readOnly = false)
	public void delete(Long id) {

        try {
            userRepository.delete(id);
        } catch (EmptyResultDataAccessException e){
            throw new UserNotFoundException();
        }
	}

}
