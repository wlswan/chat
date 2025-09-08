package com.example.chat;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ChatController {

    @GetMapping("/login")
    public String login() {
        return "login"; }

    @MessageMapping("/chat.send")
    @SendTo("/topic/messages")
    public ChatMessage sendMessage(ChatMessage message, Authentication auth) {
        return new ChatMessage(ChatMessage.MessageType.CHAT, auth.getName(), message.getContent());
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/messages")
    public ChatMessage addUser(Authentication auth) {
        return new ChatMessage(ChatMessage.MessageType.JOIN, auth.getName(), auth.getName() + " 님이 입장했습니다.");
    }

    @MessageMapping("/chat.leaveUser")
    @SendTo("/topic/messages")
    public ChatMessage leaveUser(Authentication auth) {
        return new ChatMessage(ChatMessage.MessageType.LEAVE, auth.getName(), auth.getName() + " 님이 퇴장했습니다.");
    }

}


