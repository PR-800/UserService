package com.example.userservice.query.rest;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class UserRestModel {
    private String email;
    private String username;
    private String password;
    private List role;
    private Date createdDate;
    private Date birthDate;
}
