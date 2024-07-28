package com.workshop.back.Controller;

import com.workshop.back.Entity.Message;
import com.workshop.back.Service.MessageService;
import com.workshop.back.Service.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor

@RequestMapping("/api/message")
public class MessageController {
    @Autowired
    private final MessageService service;
    private final StorageService storageService;
    @PostMapping()
            public Message sendMessage(@RequestParam(value = "image" , required = false)MultipartFile file,@RequestParam String senderId, @RequestParam String recipientId,@RequestParam String content) throws IOException {
                Message message=new Message();
                message.setSenderId(senderId);
                message.setRecipientId(recipientId);
                message.setContent(content);
                message.setTimestamp(LocalDateTime.now());
                if(file!= null ){
                message.setImage(storageService.uploadImageToFileSystem(file));}
                return service.saveMessage(message);
            }
    @GetMapping
    public List<Message> getMessages(@RequestParam String senderId, @RequestParam String recipientId) {
        return service.getMessages(senderId, recipientId);
    }
    @GetMapping("/notif")
    public List<Message> getNotifs( @RequestParam String recipientId) {
        return service.notifications( recipientId);
    }

    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public Message sendMessages(@Payload Message message){
        message.setTimestamp(LocalDateTime.now());
        return message;
    }
}
