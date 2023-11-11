package com.example.userservice.query.rest;

public class FindUserByIdQuery {
    private final String userId;

    public FindUserByIdQuery(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }
}
