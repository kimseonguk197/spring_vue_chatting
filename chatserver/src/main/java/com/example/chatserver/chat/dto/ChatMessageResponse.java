package com.example.chatserver.chat.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageResponse {
    private Long roomId;
    private Long unreadMessagesCount;
}
