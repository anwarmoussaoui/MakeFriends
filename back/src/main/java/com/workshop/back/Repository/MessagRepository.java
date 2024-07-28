package com.workshop.back.Repository;

import com.workshop.back.Entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessagRepository extends JpaRepository<Message,Long> {
    List<Message> findBySenderIdAndRecipientId(String senderId, String recipientId);
    List<Message> findByRecipientId(String recipientId);
}
