package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "chats")
public class ChatEntity implements Serializable {
    public ChatEntity(Long id) {
        this.id = id;
    }

    @Id
    @Column(name = "chat_id")
    @GeneratedValue
    private Long id;
    @Column(name = "chat_name")
    private String name;

}
