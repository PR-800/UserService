package com.example.userservice.core.data;

import jakarta.persistence.Column;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@Document("User")
public class UserEntity implements Serializable{
    @Id
    @Column(unique = true)
    private String userId;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String username;

    private String password;
    private List role;
    private Date createdDate;
    private Date birthDate;

    public UserEntity() {}

    public UserEntity(String userId, String email, String username, String password, List role, Date createdDate, Date birthDate) {
        this.userId = userId;
        this.email = email;
        this.username = username;
        this.password = password;
        this.role = role;
        this.createdDate = createdDate;
        this.birthDate = birthDate;
    }
}
