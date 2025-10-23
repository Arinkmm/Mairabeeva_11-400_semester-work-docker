package com.racetalk.service;

import com.racetalk.entity.User;

import java.util.Optional;

public interface UserService {
    void registerUser(String username, String password);

    Optional<User> loginUser(String username, String password);

    Optional<User> getByUsername(String username);

    boolean isUsernameUnique(String username);
}
