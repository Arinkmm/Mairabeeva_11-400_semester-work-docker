package com.racetalk.service;

import com.racetalk.entity.User;
import com.racetalk.entity.UserRole;

import java.io.InputStream;
import java.util.Optional;

public interface UserService {
    void registerUser(String username, String password);

    Optional<User> loginUser(String username, String password);

    void editUser(User user, InputStream photoInputStream);

    Optional<User> getById(int id);

    boolean isUsernameUnique(String username);

    boolean verifyPassword(User user, String password);

    boolean validatePassword(String password);

    String hashPassword(String password);

    boolean validateUsername(String username);
}
