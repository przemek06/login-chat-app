package com.example.demo.service;

import com.example.demo.entity.ChatEntity;
import com.example.demo.repository.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatService {

    @Autowired
    ChatRepository chatRepository;

    public boolean isIdInDatabase(Long id){
        return chatRepository.findChatEntityById(id).isPresent();
    }

    public ResponseEntity<List<ChatEntity>> findAll(){
        return ResponseEntity.ok(chatRepository.findAll());
    }

}
