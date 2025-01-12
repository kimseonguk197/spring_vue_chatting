package com.example.chatserver.chat.controller;

import com.example.chatserver.chat.domain.ChatMessage;
import com.example.chatserver.chat.domain.ChatRoom;
import com.example.chatserver.chat.dto.ChatMessageResponse;
import com.example.chatserver.chat.service.ChatService;
import com.example.chatserver.common.dto.CommonResDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chat")
public class ChatController {
    @Autowired
    private ChatService chatService;
//    @GetMapping("/history/{roomId}")
//    public ResponseEntity<?> getChatHistory(@PathVariable Long roomId) {
//        // DB 또는 메모리에서 roomId에 해당하는 채팅 기록을 조회
//        List<ChatMessage> chatHistory = chatService.getChatHistory(roomId);
//
//        CommonResDto commonResDto = new CommonResDto(HttpStatus.CREATED, "member is successfully created", chatHistory);
//        return new ResponseEntity<>(commonResDto, HttpStatus.CREATED);
//    }

    @PostMapping("/room/private")
    public ResponseEntity<Long> getOrCreateChatRoom(@RequestParam Long otherMemberId) {
        Long roomId = chatService.findOrCreateChatRoom(otherMemberId);
        return ResponseEntity.ok(roomId);
    }


    @GetMapping("/rooms")
    public ResponseEntity<List<ChatMessageResponse>> getChatRooms() {
        String memberEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        List<ChatMessageResponse> chatRooms = chatService.getChatRoomsWithUnreadCount(memberEmail);
        return ResponseEntity.ok(chatRooms);
    }
}
