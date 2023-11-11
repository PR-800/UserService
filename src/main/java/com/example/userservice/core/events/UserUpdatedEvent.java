package com.example.userservice.core.events;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class UserUpdatedEvent {
    private String userId;
    private String email;
    private String username;
    private String password;
    private List role;
    private Date createdDate;
    private Date birthDate;
}
