package com.example.safeshe.model;

public class ChatMessage {
    private String message;
    private boolean isUser;
    private String timestamp;

    public ChatMessage(String message, boolean isUser, String timestamp) {
        this.message = message;
        this.isUser = isUser;
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public boolean isUser() {
        return isUser;
    }

    public String getTimestamp() {
        return timestamp;
    }
}


