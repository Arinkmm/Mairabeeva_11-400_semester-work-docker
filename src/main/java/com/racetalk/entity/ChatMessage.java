package com.racetalk.entity;

import java.time.LocalDateTime;

public class ChatMessage {
    private int id;
    private User user;
    private String content;
    private LocalDateTime createdAt;

    public ChatMessage() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
