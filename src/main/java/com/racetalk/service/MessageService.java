package com.racetalk.service;

import com.racetalk.entity.Message;

import java.util.List;

public interface MessageService {
    public void postMessage(Message message);

    public List<Message> getAllMessages();
}
