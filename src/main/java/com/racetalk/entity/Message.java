package com.racetalk.entity;

import java.time.LocalDateTime;

public class Message {
    private int id;
    private User user;
    private String content;
    private LocalDateTime createdAt;

    public Message() {}

    public Message(int id, User user, String content, LocalDateTime createdAt) {
        this.id = id;
        this.user = user;
        this.content = content;
        this.createdAt = createdAt;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
