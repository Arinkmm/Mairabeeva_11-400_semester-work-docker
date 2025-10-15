package com.racetalk.dao;

import com.racetalk.entity.Message;

import java.util.List;

public interface MessageDao {
    public void create(Message message);

    public List<Message> findAll();
}
