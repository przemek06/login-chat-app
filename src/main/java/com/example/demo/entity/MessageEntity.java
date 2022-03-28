package com.example.demo.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@Table(name = "messages")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageEntity implements Serializable {

    @Id
    @Column(name = "message_id")
    @GeneratedValue
    private Long id;
    @Column(name = "username")
    private String username;
    @Column(name = "payload")
    private String message;
    @Column(name = "date_sent")
    private Date date;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "chat_ref", referencedColumnName = "chat_id")
    private ChatEntity chatEntity;
}
