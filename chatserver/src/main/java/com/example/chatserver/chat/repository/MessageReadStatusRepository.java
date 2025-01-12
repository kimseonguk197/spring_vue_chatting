package com.example.chatserver.chat.repository;

import com.example.chatserver.chat.domain.MessageReadStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MessageReadStatusRepository extends JpaRepository<MessageReadStatus, Long> {
    Optional<MessageReadStatus> findByMessageIdAndMemberId(Long messageId, Long memberId);

    Long countByChatRoomIdAndMemberIdAndIsReadFalse(Long chatRoomId, Long memberId);
}

