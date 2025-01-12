package com.example.chatserver.chat.service;

import com.example.chatserver.chat.domain.ChatMessage;
import com.example.chatserver.chat.domain.ChatParticipant;
import com.example.chatserver.chat.domain.ChatRoom;
import com.example.chatserver.chat.domain.MessageReadStatus;
import com.example.chatserver.chat.dto.ChatMessageResponse;
import com.example.chatserver.chat.repository.ChatParticipantRepository;
import com.example.chatserver.chat.repository.ChatRoomRepository;
import com.example.chatserver.chat.repository.MessageReadStatusRepository;
import com.example.chatserver.member.domain.Member;
import com.example.chatserver.member.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChatService {
    @Autowired
    private ChatRoomRepository chatRoomRepository;
    @Autowired
    private ChatParticipantRepository chatParticipantRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private MessageReadStatusRepository messageReadStatusRepository;

    public Long findOrCreateChatRoom(Long otherMemberId) {

        Member member = memberRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(()->new EntityNotFoundException("not found"));
       // 기존 채팅방 검색
        List<ChatRoom> existingRoom1 = chatRoomRepository.findByMemberIdAndIsOpenChat(member.getId(), "N");
        List<ChatRoom> existingRoom2 = chatRoomRepository.findByMemberIdAndIsOpenChat(otherMemberId, "N");
        for (ChatRoom room : existingRoom1) {
            boolean participantExists = room.getChatParticipants().stream()
                    .anyMatch(participant -> participant.getMember().getId().equals(otherMemberId));
            if (participantExists) {
                return room.getId();
            }
        }
        for (ChatRoom room : existingRoom2) {
            boolean participantExists = room.getChatParticipants().stream()
                    .anyMatch(participant -> participant.getMember().getId().equals(member.getId()));
            if (participantExists) {
                return room.getId();
            }
        }

        // 기존 채팅방이 없는 경우 새로 생성
        ChatRoom newRoom = ChatRoom.builder()
                .member(member)
                .isOpenChat("N")
                .build();
        chatRoomRepository.save(newRoom);

        // 채팅 참여자 추가
        ChatParticipant starter = ChatParticipant.builder()
                .chatRoom(newRoom)
                .member(member)
                .build();
        ChatParticipant other = ChatParticipant.builder()
                .chatRoom(newRoom)
                .member(memberRepository.findById(otherMemberId).orElseThrow(()->new EntityNotFoundException("not found")))
                .build();
        chatParticipantRepository.save(starter);
        chatParticipantRepository.save(other);
        return newRoom.getId();
    }
    public List<ChatMessageResponse> getChatRoomsWithUnreadCount() {
        Member member = memberRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(()->new EntityNotFoundException("not found"));

        List<ChatParticipant> chatParticipants = chatParticipantRepository.findAllByMemberId(member.getId());
        List<ChatRoom> chatRooms = new ArrayList<>();
        List<ChatMessageResponse> chatMessageResponses = new ArrayList<>();
        for(ChatParticipant c : chatParticipants){
            ChatMessageResponse chatMessage = ChatMessageResponse.builder()
                    .roomId(c.getChatRoom().getId())
                    .
                    .build();
        }
    }
}
