package com.Beeznes.controller;

import com.Beeznes.model.User;
import com.Beeznes.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService service;

    @MessageMapping("/user.addUser")
    @SendTo("/user/public")
    public User addUser(
           @Payload User user) {
        service.saveUser(user);
        return user;
    }

    @MessageMapping("/user.disconnectUser")
    @SendTo("/user/public")
    public User disconnect(@Payload User user) {
        service.disconnectUser(user);
        return user;
    }
    @GetMapping("/users")
    public ResponseEntity <List<User>> fiindConnectedUsers() {
        return ResponseEntity.ok(service.findConnectedUsers());
    }
}
