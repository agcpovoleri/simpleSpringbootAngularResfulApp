package com.example.forumapp.repository;


import com.example.forumapp.entity.UserLogin;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public interface UserLoginRepository extends CrudRepository<UserLogin, Long> {

    UserLogin findByUsername(String username);
}
