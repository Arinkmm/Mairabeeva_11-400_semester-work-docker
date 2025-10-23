package com.racetalk.service.impl;

import com.racetalk.dao.UserDao;
import com.racetalk.entity.User;
import com.racetalk.service.UserService;
import com.racetalk.util.PasswordHasherUtil;

import java.util.Optional;

public class UserServiceImpl implements UserService {
    private final UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void registerUser(String username, String password) {
        String hashedPassword = PasswordHasherUtil.hashPassword(password);
        userDao.create(new User(username, hashedPassword));
    }

    @Override
    public Optional<User> loginUser(String username, String password) {
        Optional<User> loginUser = userDao.findByUsername(username);
        return loginUser.filter(u -> PasswordHasherUtil.checkPassword(password, u.getPassword()));
    }

    @Override
    public Optional<User> getByUsername(String username) {
        return userDao.findByUsername(username);
    }

    @Override
    public boolean isUsernameUnique(String username) {
        return userDao.findByUsername(username).isEmpty();
    }
}
