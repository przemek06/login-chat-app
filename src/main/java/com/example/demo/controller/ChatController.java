package com.example.demo.controller;

import com.example.demo.entity.ChatEntity;
import com.example.demo.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:63342", allowCredentials = "true")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @GetMapping("/groups")
    public ResponseEntity<List<ChatEntity>>  findAll(){
        return chatService.findAll();
    }
}
