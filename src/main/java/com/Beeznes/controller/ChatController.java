package com.Beeznes.controller;

import com.Beeznes.model.ChatMessage;
import com.Beeznes.model.ChatNotification;
import com.Beeznes.service.ChatMessageService;
import com.Beeznes.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ChatController {
    private final SimpMessagingTemplate messagingTemplate;
    private final ChatMessageService chatMessageService;

    @MessageMapping("/chat")
    public void processMessage(@Payload ChatMessage chatMessage) {
        ChatMessage savedMsg = chatMessageService.save(chatMessage);
        messagingTemplate.convertAndSendToUser(
                chatMessage.getRecipientId().toString(), "/queue/messages",
                ChatNotification.builder()
                        .id(savedMsg.getId())
                        .senderId(savedMsg.getSenderId())
                        .receiverId(savedMsg.getRecipientId())
                        .content(savedMsg.getContent()).build()
        );
    }

    @GetMapping("/messages/{senderId}/{recipientId}")
    public ResponseEntity <List<ChatMessage>> findChatMessages(
            @PathVariable("senderId") Long senderId,
            @PathVariable("recipientId") Long recipientId){
        return ResponseEntity.ok(chatMessageService.findChatMessages(senderId, recipientId));
    }
}
