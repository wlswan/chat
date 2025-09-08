package com.example.chat;

import lombok.Data;

@Data
public class ChatMessage {
    public enum MessageType {
        CHAT,
        JOIN,
        LEAVE
    }

    private MessageType type;
    private String from;
    private String content;

    public ChatMessage() {}

    public ChatMessage(MessageType type, String from, String content) {
        this.type = type;
        this.from = from;
        this.content = content;
    }

}


