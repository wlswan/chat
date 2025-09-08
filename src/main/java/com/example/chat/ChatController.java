package com.example.chat;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ChatController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/room")
    public String roomPage() {
        return "room"; // room.html 반환
    }
    @GetMapping("/chat")
    public String chatPage() {
        return "chat";
    }

    @GetMapping("/chat/{roomId}")
    public String chat(@PathVariable String roomId, Model model) {
        model.addAttribute("roomId", roomId);
        return "chat";
    }

    @MessageMapping("/chat.send/{roomId}") //postMapping처럼 이 주소로 오면 처리
    @SendTo("/topic/room/{roomId}")  //어디로 브로드캐스트할지
    public ChatMessage sendToRoom(@DestinationVariable String roomId,
                                  ChatMessage message,
                                  Authentication auth) {
        return new ChatMessage(ChatMessage.MessageType.CHAT,
                auth.getName(),
                message.getContent());
    }

    @MessageMapping("/chat.addUser/{roomId}")
    @SendTo("/topic/room/{roomId}")
    public ChatMessage addUserToRoom(@DestinationVariable String roomId, Authentication auth) {
        return new ChatMessage(ChatMessage.MessageType.JOIN,
                auth.getName(),
                auth.getName() + " 님이 방(" + roomId + ")에 입장했습니다.");
    }

    @MessageMapping("/chat.leaveUser/{roomId}")
    @SendTo("/topic/room/{roomId}")
    public ChatMessage leaveUserFromRoom(@DestinationVariable String roomId, Authentication auth) {
        return new ChatMessage(ChatMessage.MessageType.LEAVE,
                auth.getName(),
                auth.getName() + " 님이 방(" + roomId + ")에서 퇴장했습니다.");
    }

    @MessageMapping("/chat.send/global")
    @SendTo("/topic/global")
    public ChatMessage sendMessage(ChatMessage message, Authentication auth) {
        return new ChatMessage(ChatMessage.MessageType.CHAT, auth.getName(), message.getContent());
    }

    @MessageMapping("/chat.addUser/global")
    @SendTo("/topic/global")
    public ChatMessage addUser(Authentication auth) {
        return new ChatMessage(ChatMessage.MessageType.JOIN, auth.getName(), auth.getName() + " 님이 입장했습니다.");
    }

    @MessageMapping("/chat.leaveUser/global")
    @SendTo("/topic/global")
    public ChatMessage leaveUser(Authentication auth) {
        return new ChatMessage(ChatMessage.MessageType.LEAVE, auth.getName(), auth.getName() + " 님이 퇴장했습니다.");
    }

}


