package com.example.demo.model;

import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class MessageModel {

    public MessageModel(String message) {
        this.message = message;
    }

    private String username;
    private String message;
    private Date date;

}
