package com.racetalk.service;

import com.racetalk.entity.User;

import java.util.Optional;

public interface UserService {
    public void registerUser(User user);

    public Optional<User> loginUser(User user);

    public Optional<User> findById(int id);
}
