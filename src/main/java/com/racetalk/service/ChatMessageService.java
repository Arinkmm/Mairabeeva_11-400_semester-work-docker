package com.racetalk.service;

import com.racetalk.entity.ChatMessage;

import java.util.List;

public interface ChatMessageService {
    void postMessage(ChatMessage chatMessage);

    List<ChatMessage> getAllMessages();
}
