package com.example.demo.repository;

import com.example.demo.entity.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;


@Repository
public interface MessageRepository extends JpaRepository<MessageEntity, Long> {


    @Query(nativeQuery = true,
            value = "select * from messages m where m.chat_ref=:id and m.date_sent < :date order by m.date_sent desc limit :limit")
    List<MessageEntity> findAllByChatId(Long id, Date date, int limit);
}
