package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

@Data
@Entity
@Table(name="user")
@ToString
@AllArgsConstructor
@Builder
public class UserEntity {

    public UserEntity() {
    }

    public UserEntity(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "id_seq"
    )
    @SequenceGenerator(
            name = "id_seq",
            sequenceName = "id_seq"
    )
    private Long id;
    private String username;
    private String password;
    private String roles;
    private boolean active;

}
