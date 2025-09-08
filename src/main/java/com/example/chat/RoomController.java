package com.example.chat;

import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/rooms")
public class RoomController {

    private final Map<String, ChatRoom> rooms = new ConcurrentHashMap<>();

    @PostMapping
    public ChatRoom createRoom(@RequestParam String name) {
        String roomId = UUID.randomUUID().toString();
        ChatRoom room = new ChatRoom();
        room.setRoomId(roomId);
        room.setName(name);
        rooms.put(roomId, room);
        return room;
    }

    @GetMapping
    public Collection<ChatRoom> listRooms(){
        return rooms.values();
    }
}
