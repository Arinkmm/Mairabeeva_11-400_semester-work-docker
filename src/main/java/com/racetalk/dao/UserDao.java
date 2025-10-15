package com.racetalk.dao;

import com.racetalk.entity.User;

import java.util.Optional;

public interface UserDao {
    public void create(User user);

    public Optional<User> findByUsername(String username);

    public Optional<User> findById(int id);
}
