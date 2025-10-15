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
    public void registerUser(User user) {
        userDao.create(user);
    }

    @Override
    public Optional<User> loginUser(User user) {
        Optional<User> loginUser = userDao.findByUsername(user.getUsername());
        return loginUser.filter(u -> u.getPassword().equals(user.getPassword()));
    }

    @Override
    public Optional<User> findById(int id) {
        return userDao.findById(id);
    }

}
