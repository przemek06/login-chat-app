package com.example.demo.service;

import com.example.demo.batch.MessageBuffer;
import com.example.demo.entity.ChatEntity;
import com.example.demo.entity.MessageEntity;
import com.example.demo.model.MessageModel;
import com.example.demo.model.MessageRequest;
import com.example.demo.model.UserDetailsImpl;
import com.example.demo.repository.MessageRepository;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class MessageService {
    final static int LIMIT = 25;
    final static int MESSAGE_LENGTH = 255;

    @Autowired
    MessageBuffer<MessageEntity> messageBuffer;

    @Autowired
    ChatService chatService;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    MessageRepository messageRepository;

    public ResponseEntity<List<MessageEntity>> getLimitedMessagesByChatId(Long id, MessageRequest messageRequest){


        List<MessageEntity> messages = messageBuffer.getMessages(id,
                new Date(Long.parseLong(messageRequest.getDate())),
                LIMIT);

        if(messages.size()<LIMIT){
            messages.addAll(messageRepository.findAllByChatId(id,
                    new Date(Long.parseLong(messageRequest.getDate())),
                    LIMIT-messages.size()));
        }

        HttpHeaders responseHeaders = new HttpHeaders();
        if(!messages.isEmpty()){
            responseHeaders.set("last-message",
                    String.valueOf(messages.get(messages.size()-1).getDate().getTime()));
            responseHeaders.put("Access-Control-Expose-Headers", Collections.singletonList("last-message"));
        }

        return ResponseEntity.ok().headers(responseHeaders).body(messages);
    }

    public void sendMessage(Long to, MessageModel message)
            throws JobParametersInvalidException, JobExecutionAlreadyRunningException,
            JobRestartException, JobInstanceAlreadyCompleteException {

        if(chatService.isIdInDatabase(to) && message.getMessage().length()< MESSAGE_LENGTH){
          String username = ((UserDetailsImpl) SecurityContextHolder.getContext()
                    .getAuthentication().getPrincipal()).getUsername();
            message.setUsername(username);
            message.setDate(new Date());

            MessageEntity messageEntity = MessageEntity.builder()
                    .username(username)
                    .message(message.getMessage())
                    .date(message.getDate()).build();

            messageEntity.setChatEntity(new ChatEntity(to));
            messageBuffer.save(messageEntity);

            simpMessagingTemplate.convertAndSend("/topic/messages/" + to,message);
        }
    }
}
