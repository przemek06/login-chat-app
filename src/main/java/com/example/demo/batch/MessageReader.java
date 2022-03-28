package com.example.demo.batch;

import com.example.demo.entity.MessageEntity;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class MessageReader implements ItemReader<MessageEntity> {

    @Autowired
    MessageBuffer<MessageEntity> messageBuffer;

    @Override
    public MessageEntity read() throws UnexpectedInputException, ParseException, NonTransientResourceException {
        if (messageBuffer.getMessages().size() > 0) {
            return messageBuffer.remove(0);
        } else return null;
    }
}
