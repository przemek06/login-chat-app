package com.example.demo.batch;

import com.example.demo.entity.MessageEntity;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class MessageBufferImpl implements MessageBuffer<MessageEntity>{

    final static int CAPACITY=20;
    List<MessageEntity> messages;
    @Autowired
    JobLauncher jobLauncher;
    @Autowired
    Job job;

    public MessageBufferImpl() {
        this.messages = new ArrayList<>();
    }

    public void save(MessageEntity messageEntity) throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
        messages.add(messageEntity);
        if(messages.size()>=CAPACITY) uploadAndClear();
    }

    public List<MessageEntity> getMessages(long id, Date date, int limit){
        List<MessageEntity> properMessages= messages.stream()
                .filter(m->m.getChatEntity().getId().equals(id) && m.getDate().before(date))
                .collect(Collectors.toList());
        if(properMessages.size()<limit) return  properMessages;
        else return  properMessages.subList(0, limit);
    }

    public List<MessageEntity> getMessages() {
        return messages;
    }

    @Override
    public MessageEntity remove(int index) {
        return messages.remove(index);
    }

    public void uploadAndClear() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
        Map<String, JobParameter> maps = new HashMap<>();
        maps.put("time", new JobParameter(System.currentTimeMillis()));
        JobParameters parameters = new JobParameters(maps);
        JobExecution jobExecution = jobLauncher.run(job, parameters);

        while (jobExecution.isRunning()) {
            System.out.println("...");
        }
    }

    @PreDestroy
    public void destroy() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
        uploadAndClear();
    }
}
