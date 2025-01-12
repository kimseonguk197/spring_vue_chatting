package com.example.chatserver.chat.dto;

import com.example.chatserver.chat.domain.ChatMessage;
import com.example.chatserver.chat.domain.ChatRoom;
import com.example.chatserver.member.domain.Member;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ChatMessageRequest {
    private String content;
    private String sender;
}
