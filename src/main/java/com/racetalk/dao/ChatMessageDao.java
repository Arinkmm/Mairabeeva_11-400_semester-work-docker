package com.racetalk.dao;

import com.racetalk.entity.ChatMessage;

import java.util.List;

public interface ChatMessageDao {
    void create(ChatMessage chatMessage);

    List<ChatMessage> findAll();
}
