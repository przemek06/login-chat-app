package com.example.demo.batch;

import com.example.demo.entity.MessageEntity;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class MessageProcessor implements ItemProcessor<MessageEntity, MessageEntity> {

    @Override
    public MessageEntity process(MessageEntity messageEntity) {
        return messageEntity;
    }
}
