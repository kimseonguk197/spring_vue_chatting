package com.example.chatserver.chat.controller;

import com.example.chatserver.chat.domain.ChatMessage;
import com.example.chatserver.chat.dto.ChatMessageRequest;
import com.example.chatserver.chat.service.WebsocketService;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebsocketController {

    private final WebsocketService websocketService;

    public WebsocketController(WebsocketService websocketService) {
        this.websocketService = websocketService;
    }
    @MessageMapping("/{roomId}") // 클라이언트가 특정 roomId로 메시지를 전송
    @SendTo("/sub/messages/{roomId}") // 해당 roomId를 구독 중인 클라이언트에게 메시지 전송
    public ChatMessageRequest sendMessage(@DestinationVariable String roomId, ChatMessageRequest message) {
        websocketService.saveMessage(roomId, message);
        return message; // 메시지를 그대로 반환
    }

}
