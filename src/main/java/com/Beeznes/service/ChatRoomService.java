
package com.Beeznes.service;

import com.Beeznes.model.ChatRoom;
import com.Beeznes.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;

    public Optional<Long> getChatRoomId(Long senderId, Long receiverId, boolean createNewRoomIfNotExist){
        return chatRoomRepository.findBySenderIdAndRecipientId(senderId,receiverId).map(ChatRoom::getChatId).or(()->{
            if(createNewRoomIfNotExist){
                Long chatId=createChatId(senderId,receiverId);
                return Optional.of(chatId);
            }
            return Optional.empty();
        });
    }
    private Long createChatId(Long senderId, Long receiverId) {
        String chatIdString = String.format("%d_%d", senderId, receiverId);

        // Convertir la cadena concatenada a un Long (esto puede no ser óptimo en todos los casos)
        Long chatId = (long) chatIdString.hashCode();

        ChatRoom senderRecipient= ChatRoom.builder()
                .chatId(chatId)
                .senderId(senderId)
                .recipientId(receiverId)
                .build();
        ChatRoom recipientSender= ChatRoom.builder()
                .chatId(chatId)
                .senderId(receiverId)
                .recipientId(senderId)
                .build();
        chatRoomRepository.save(senderRecipient);
        chatRoomRepository.save(recipientSender);
        return chatId;
    }
}
