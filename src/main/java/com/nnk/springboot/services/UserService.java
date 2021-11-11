package com.nnk.springboot.services;

import com.nnk.springboot.domain.User;

import java.util.List;

public interface UserService {
    User findByUsername(String username);
    List<User> findAll();
    User findById(int id);
    Boolean updateUser(int id, User user);
    void save(User user);
    void delete(User user);
}
