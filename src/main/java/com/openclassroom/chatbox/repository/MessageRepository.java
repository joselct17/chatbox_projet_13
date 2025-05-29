package com.openclassroom.chatbox.repository;


import com.openclassroom.chatbox.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findBySenderIdOrReceiverId(Long senderId, Long receiverId);
}
