package com.example.demo.batch;

import com.example.demo.entity.MessageEntity;
import com.example.demo.repository.MessageRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MessageWriter implements ItemWriter<MessageEntity> {

    @Autowired
    MessageRepository repository;

    @Override
    public void write(List<? extends MessageEntity> list) {
        repository.saveAll(list);
    }
}
