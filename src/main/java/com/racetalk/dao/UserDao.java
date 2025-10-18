package com.racetalk.dao;

import com.racetalk.entity.User;

import java.util.Optional;

public interface UserDao {
    void create(User user);

    Optional<User> findByUsername(String username);

    Optional<User> findById(int id);
}
