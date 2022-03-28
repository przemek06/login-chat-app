package com.example.demo.repository;

import com.example.demo.entity.ChatEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface ChatRepository extends JpaRepository<ChatEntity, Long> {
    Optional<ChatEntity> findChatEntityById(Long id);
}
