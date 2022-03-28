package com.example.demo.batch;


import com.example.demo.entity.MessageEntity;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;

import java.util.Date;
import java.util.List;

public interface MessageBuffer<T> {
    void save(T t) throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException;
    List<T> getMessages(); //changed
    List<T> getMessages(long id, Date date, int limit);
    void uploadAndClear() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException;
    MessageEntity remove(int index);
}
