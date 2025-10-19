package com.racetalk.service.impl;

import com.racetalk.dao.UserDao;
import com.racetalk.entity.User;
import com.racetalk.service.UserService;
import com.racetalk.util.PasswordHasherUtil;

import java.util.Optional;

public class UserServiceImpl implements UserService {
    private final UserDao userDao;
    private final PasswordHasherUtil passwordHasherUtil = new PasswordHasherUtil();

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void registerUser(String username, String password) {
        String hashedPassword = passwordHasherUtil.hashPassword(password);
        userDao.create(new User(username, hashedPassword));
    }

    @Override
    public Optional<User> loginUser(String username, String password) {
        Optional<User> loginUser = userDao.findByUsername(username);
        return loginUser.filter(u -> passwordHasherUtil.checkPassword(password, u.getPassword()));
    }

    @Override
    public Optional<User> getById(int id) {
        return userDao.findById(id);
    }
}
