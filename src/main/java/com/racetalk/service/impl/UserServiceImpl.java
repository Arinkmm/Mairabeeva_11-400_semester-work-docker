package com.racetalk.service.impl;

import com.cloudinary.Cloudinary;
import com.racetalk.dao.UserDao;
import com.racetalk.entity.User;
import com.racetalk.entity.UserRole;
import com.racetalk.exception.DataAccessException;
import com.racetalk.exception.ServiceException;
import com.racetalk.service.UserService;
import com.racetalk.util.PasswordHasherUtil;
import com.racetalk.util.PasswordValidator;
import com.racetalk.util.UsernameValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserDao userDao;
    private final Cloudinary cloudinary;

    public UserServiceImpl(UserDao userDao, Cloudinary cloudinary) {
        this.userDao = userDao;
        this.cloudinary = cloudinary;
    }

    @Override
    public void registerUser(String username, String password) {
        String hashedPassword = PasswordHasherUtil.hashPassword(password);
        User user = new User();
        user.setUsername(username);
        user.setPassword(hashedPassword);
        user.setRole(UserRole.USER);
        try {
            userDao.create(user);
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
    public void editUser(User user, InputStream photoInputStream) {
        Map uploadResult;
        try {
            if (photoInputStream != null && photoInputStream.available() > 0) {
                byte[] photoBytes = photoInputStream.readAllBytes();

                uploadResult = cloudinary.uploader().upload(photoBytes, new HashMap<>());

                String photoUrl = (String) uploadResult.get("secure_url");
                user.setPhoto(photoUrl);
            }
            userDao.update(user);
        } catch (Exception e) {
            logger.error("Failed to update user", e);
            throw new ServiceException("Failed to update user with photo", e);
        }
    }

    @Override
    public void deleteUser(int id) {
        try {
            userDao.deleteById(id);
        } catch (DataAccessException e) {
            logger.error("Failed to delete user with id {}", id, e);
            throw new ServiceException("Failed to delete user", e);
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
    public boolean isAdmin(User user) {
        return user != null && user.getRole() == UserRole.ADMIN;
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

    @Override
    public boolean verifyPassword(User user, String password) {
        if (user == null || password == null) {
            return false;
        }
        String hashedPassword = user.getPassword();
        if (hashedPassword == null) {
            return false;
        }
        return PasswordHasherUtil.checkPassword(password, hashedPassword);
    }


    @Override
    public boolean validatePassword(String password) {
        return PasswordValidator.isValid(password);
    }

    @Override
    public String hashPassword(String password) {
        return PasswordHasherUtil.hashPassword(password);
    }

    @Override
    public boolean validateUsername(String username) {
        return UsernameValidator.isValid(username);
    }


}
