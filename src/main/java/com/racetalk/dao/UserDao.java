package com.racetalk.dao;

import com.racetalk.entity.User;

import java.util.Optional;

public interface UserDao {
    void create(User user);

    void update(User user);

    void deleteById(int id);

    Optional<User> findByUsername(String username);

    Optional<User> findById(int id);
}
