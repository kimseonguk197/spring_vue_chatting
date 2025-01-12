package com.example.chatserver.chat.service;

import com.example.chatserver.chat.domain.ChatMessage;
import com.example.chatserver.chat.domain.ChatRoom;
import com.example.chatserver.chat.domain.MessageReadStatus;
import com.example.chatserver.chat.dto.ChatMessageRequest;
import com.example.chatserver.chat.repository.ChatMessageRepository;
import com.example.chatserver.chat.repository.ChatRoomRepository;
import com.example.chatserver.chat.repository.MessageReadStatusRepository;
import com.example.chatserver.member.domain.Member;
import com.example.chatserver.member.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WebsocketService {

    private final ChatRoomRepository chatRoomRepository;
    private final MemberRepository memberRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final MessageReadStatusRepository messageReadStatusRepository;

    public void saveMessage(String roomId, ChatMessageRequest messageRequest) {
        // 채팅방 조회
        ChatRoom chatRoom = chatRoomRepository.findById(Long.valueOf(roomId))
                .orElseThrow(() -> new EntityNotFoundException("Chat room not found"));

        // 보낸 사람 조회
        Member sender = memberRepository.findByEmail(messageRequest.getSender())
                .orElseThrow(() -> new EntityNotFoundException("Sender not found"));

        // 메시지 저장
        ChatMessage chatMessage = ChatMessage.builder()
                .chatRoom(chatRoom)
                .content(messageRequest.getContent())
                .member(sender)
                .build();

        chatMessageRepository.save(chatMessage);


        // 모든 참여자에 대해 읽음 상태 추가
        chatRoom.getChatParticipants().forEach(participant -> {
            MessageReadStatus readStatus = MessageReadStatus.builder()
                    .message(chatMessage)
                    .member(participant.getMember())
                    .isRead(participant.getMember().equals(sender)) // 보낸 사람은 이미 읽음 상태
                    .build();
            messageReadStatusRepository.save(readStatus);
        });
    }
}
