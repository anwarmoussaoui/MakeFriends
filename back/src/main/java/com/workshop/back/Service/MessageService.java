package com.workshop.back.Service;

import com.workshop.back.Entity.Message;
import com.workshop.back.Repository.MessagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor

public class MessageService {
    private final MessagRepository repos;

    public List<Message> getMessages(String senderId, String recipientId) {
        List<Message> messages = repos.findBySenderIdAndRecipientId(recipientId,senderId );
        messages.addAll(repos.findBySenderIdAndRecipientId(senderId, recipientId));
        messages.sort(Comparator.comparing(Message::getTimestamp));
        return messages;
    }
    public List<Message> notifications(String recipientId) {
        // Fetch all messages for the recipient
        List<Message> messages = repos.findByRecipientId(recipientId);

        // Create a map to store the latest message for each senderId
        Map<String, Message> latestMessagesMap = new HashMap<>();

        // Iterate through the messages and keep track of the latest message for each senderId
        for (Message message : messages) {
            String senderId = message.getSenderId();
            if (!latestMessagesMap.containsKey(senderId) || message.getTimestamp().isAfter(latestMessagesMap.get(senderId).getTimestamp())) {
                latestMessagesMap.put(senderId, message);
            }
        }

        // Convert map values (latest messages) back to a list
        List<Message> latestMessages = new ArrayList<>(latestMessagesMap.values());

        // Sort the list by timestamp (optional, if needed)
        latestMessages.sort(Comparator.comparing(Message::getTimestamp));

        return latestMessages;
    }

    public Message saveMessage(Message message) {
        return repos.save(message);
    }
}
