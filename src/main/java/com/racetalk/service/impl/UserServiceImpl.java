package com.racetalk.service.impl;

import com.racetalk.dao.UserDao;
import com.racetalk.entity.User;
import com.racetalk.exception.DataAccessException;
import com.racetalk.exception.ServiceException;
import com.racetalk.service.UserService;
import com.racetalk.util.PasswordHasherUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void registerUser(String username, String password) {
        String hashedPassword = PasswordHasherUtil.hashPassword(password);
        try {
            userDao.create(new User(username, hashedPassword));
        } catch (DataAccessException e) {
            logger.error("Failed to register user {}", username, e);
            throw new ServiceException("Registration failed", e);
        }
    }

    @Override
    public Optional<User> loginUser(String username, String password) {
        try {
            Optional<User> userOpt = userDao.findByUsername(username);
            return userOpt.filter(u -> PasswordHasherUtil.checkPassword(password, u.getPassword()));
        } catch (DataAccessException e) {
            logger.error("Failed to login user {}", username, e);
            throw new ServiceException("Login failed", e);
        }
    }

    @Override
    public Optional<User> getById(int id) {
        try {
            return userDao.findById(id);
        } catch (DataAccessException e) {
            logger.error("Failed to get user by id {}", id, e);
            throw new ServiceException("Failed to get user", e);
        }
    }

    @Override
    public boolean isUsernameUnique(String username) {
        try {
            return userDao.findByUsername(username).isEmpty();
        } catch (DataAccessException e) {
            logger.error("Failed to check username uniqueness for {}", username, e);
            throw new ServiceException("Check username uniqueness failed", e);
        }
    }
}
