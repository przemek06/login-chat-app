package com.example.demo.config;

import com.example.demo.entity.MessageEntity;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;


@Configuration
@EnableBatchProcessing
public class BatchConfiguration  {

    @Value("${chunk.size}")
    private int size;

    @Bean
    public Job job(JobBuilderFactory jobBuilderFactory,
                   StepBuilderFactory stepBuilderFactory,
                   ItemReader<MessageEntity> itemReader,
                   ItemProcessor<MessageEntity, MessageEntity> itemProcessor,
                   ItemWriter<MessageEntity> itemWriter
    ) {

        Step step = stepBuilderFactory.get("ETL-buffer-load")
                .<MessageEntity, MessageEntity>chunk(20)
                .reader(itemReader)
                .processor(itemProcessor)
                .writer(itemWriter)
                .build();


        return jobBuilderFactory.get("ETL-load")
                .incrementer(new RunIdIncrementer())
                .start(step)
                .build();
    }
}
