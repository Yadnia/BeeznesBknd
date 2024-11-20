package com.Beeznes.model;

import jakarta.persistence.*;
import lombok.*;

import javax.print.attribute.standard.MediaSize;
import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "ChatMessage")
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "chatId")
    private Long chatId;

    @Column (name = "senderId" )
    private Long senderId;

    @Column (name = "recipientId" )
    private Long recipientId;

    @Column (name = "content" )
    private String content;

    @Column (name = "date")
    private Date timestamp;

}
