package com.example.userservice.command;

import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.Date;
import java.util.List;

@Builder
@Data
public class UpdateUserCommand {
    @TargetAggregateIdentifier
    private final String userId;
    private final String email;
    private final String username;
    private final String password;
    private final List role;
    private final Date createdDate;
    private final Date birthDate;
}
