package com.racetalk.service.impl;

import com.racetalk.dao.UserDao;
import com.racetalk.entity.User;
import com.racetalk.service.UserService;

import java.util.Optional;

public class UserServiceImpl implements UserService {
    private final UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void registerUser(String username, String password) {
        userDao.create(new User(username, password));
    }

    @Override
    public Optional<User> loginUser(String username, String password) {
        Optional<User> loginUser = userDao.findByUsername(username);
        return loginUser.filter(u -> u.getPassword().equals(password));
    }

    @Override
    public Optional<User> getById(int id) {
        return userDao.findById(id);
    }

}
