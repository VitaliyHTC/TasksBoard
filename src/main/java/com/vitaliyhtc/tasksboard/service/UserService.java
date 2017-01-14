package com.vitaliyhtc.tasksboard.service;

import com.vitaliyhtc.tasksboard.model.User;

import java.util.List;

public interface UserService {

    void save(User user);

    User findById(Long id);
    User findByUsername(String username);
    User findByEmail(String email);

    //void addUser(User user); see => void save(User user);
    void updateUser(User user, Boolean isPasswordChanged);
    void removeUser(User user);
    List<User> getAll();
}
