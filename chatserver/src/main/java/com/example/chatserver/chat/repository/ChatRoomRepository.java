package com.example.chatserver.chat.repository;

import com.example.chatserver.chat.domain.ChatRoom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {


    List<ChatRoom> findByMemberIdAndIsOpenChat(Long id, String isOpenChat);
    List<ChatRoom> findByMemberId(Long id);
}
