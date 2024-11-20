package com.Beeznes.service;

import com.Beeznes.model.ChatMessage;
import com.Beeznes.repository.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatMessageService {

    private final ChatMessageRepository repository;
    private final ChatRoomService chatRoomService;

    public ChatMessage save(ChatMessage chatMessage) {
        Long chatId = chatRoomService.getChatRoomId(chatMessage.getSenderId(),
                chatMessage.getRecipientId(),
                true
        ).orElseThrow();
        chatMessage.setChatId(chatId);
        repository.save(chatMessage);
        return chatMessage;
    }
    public List<ChatMessage> findChatMessages(Long senderId, Long recipientId) {
        // Obtiene el chatId de los usuarios o lanza una excepción si no se encuentra
        Long chatId = chatRoomService.getChatRoomId(senderId, recipientId, true)
                .orElseThrow(() -> new RuntimeException("Chat not found"));

        // Retorna la lista de mensajes asociados al chatId, o una lista vacía si no hay mensajes
        return repository.findByChatId(chatId);
    }

}
