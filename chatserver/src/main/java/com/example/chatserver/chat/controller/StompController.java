package com.example.chatserver.chat.controller;

import com.example.chatserver.chat.dto.ChatMessageDto;
import com.example.chatserver.chat.service.ChatService;
import com.example.chatserver.chat.service.RedisPubSubService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

@Controller
public class StompController {

    private final SimpMessageSendingOperations messageTemplate;
    private final ChatService chatService;
    private final RedisPubSubService pubSubService;

    public StompController(SimpMessageSendingOperations messageTemplate, ChatService chatService, RedisPubSubService pubSubService) {
        this.messageTemplate = messageTemplate;
        this.chatService = chatService;
        this.pubSubService = pubSubService;
    }
////    방법1.MessageMapping(수신)과 SenTo(topic에 메시지전달)한꺼번에 처리
//    @MessageMapping("/{roomId}") //클라이언트에서 특정 publish/roomId형태로 메시지를 발행시 MessageMapping 수신
//    @SendTo("/topic/{roomId}")  //해당 roomId에 메시지를 발행하여 구독중인 클라이언트에게 메시지 전송
////    DestinationVariable : @MessageMapping 어노테이션으로 정의된 Websocket Controller 내에서만 사용
//    public String sendMessage(@DestinationVariable Long roomId, String message){
//        System.out.println(message);
//        return  message;
//    }

//    방법2.MessageMapping어노테이션만 활용.
    @MessageMapping("/{roomId}")
    public void sendMessage(@DestinationVariable Long roomId, ChatMessageDto chatMessageReqDto) throws JsonProcessingException {
        System.out.println(chatMessageReqDto.getMessage());
        chatService.saveMessage(roomId, chatMessageReqDto);
        chatMessageReqDto.setRoomId(roomId);
//        messageTemplate.convertAndSend("/topic/"+roomId, chatMessageReqDto);
        ObjectMapper objectMapper = new ObjectMapper();
        String message = objectMapper.writeValueAsString(chatMessageReqDto);
        pubSubService.publish("chat", message);
    }
}
