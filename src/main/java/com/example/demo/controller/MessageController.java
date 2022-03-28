package com.example.demo.controller;

import com.example.demo.entity.MessageEntity;
import com.example.demo.model.MessageModel;
import com.example.demo.model.MessageRequest;
import com.example.demo.service.MessageService;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:63342", allowCredentials = "true")
public class MessageController {

    @Autowired
    MessageService messageService;

    @MessageMapping("/chat/{to}")
    public void sendMessage(@DestinationVariable Long to, MessageModel message)
            throws JobParametersInvalidException, JobExecutionAlreadyRunningException,
            JobRestartException, JobInstanceAlreadyCompleteException {
        messageService.sendMessage(to, message);
    }

    @PostMapping("/messages/{id}")
    public ResponseEntity<List<MessageEntity>> findAllByChatId( @PathVariable Long id, @RequestBody MessageRequest messageRequest){
        return messageService.getLimitedMessagesByChatId(id, messageRequest);
    }



}
