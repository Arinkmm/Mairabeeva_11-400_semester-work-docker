package com.racetalk.service.impl;

import com.racetalk.dao.MessageDao;
import com.racetalk.entity.Message;
import com.racetalk.service.MessageService;

import java.util.List;

public class MessageServiceImpl implements MessageService {
    private final MessageDao messageDao;

    public MessageServiceImpl(MessageDao messageDao) {
        this.messageDao = messageDao;
    }

    @Override
    public void postMessage(Message message) {
        messageDao.create(message);
    }

    @Override
    public List<Message> getAllMessages() {
        return messageDao.findAll();
    }
}
