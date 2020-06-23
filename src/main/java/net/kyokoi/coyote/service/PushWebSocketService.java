package net.kyokoi.coyote.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import net.kyokoi.coyote.repository.GameRepository;

@Controller
public class PushWebSocketService {

    @Autowired
    GameRepository gameRepository;

    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;

    public void sendMessage(int roomId, String message) {
        simpMessagingTemplate.convertAndSend("/topic/" + roomId, message);
    }
    
}