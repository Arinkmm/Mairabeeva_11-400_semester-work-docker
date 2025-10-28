package com.racetalk.service.impl;

import com.racetalk.dao.ChatMessageDao;
import com.racetalk.entity.ChatMessage;
import com.racetalk.exception.DataAccessException;
import com.racetalk.exception.ServiceException;
import com.racetalk.service.ChatMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ChatMessageServiceImpl implements ChatMessageService {
    private static final Logger logger = LoggerFactory.getLogger(ChatMessageServiceImpl.class);
    private final ChatMessageDao chatMessageDao;

    public ChatMessageServiceImpl(ChatMessageDao chatMessageDao) {
        this.chatMessageDao = chatMessageDao;
    }

    @Override
    public void postMessage(ChatMessage chatMessage) {
        try {
            chatMessageDao.create(chatMessage);
        } catch (DataAccessException e) {
            logger.error("Failed to post chat message", e);
            throw new ServiceException("Could not post chat message", e);
        }
    }

    @Override
    public List<ChatMessage> getAllMessages() {
        try {
            return chatMessageDao.findAll();
        } catch (DataAccessException e) {
            logger.error("Failed to get all chat messages", e);
            throw new ServiceException("Could not get all chat messages", e);
        }
    }
}
