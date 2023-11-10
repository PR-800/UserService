package com.example.userservice.command;

import com.example.userservice.core.events.UserCreatedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import java.util.Date;
import java.util.List;

@Aggregate
public class UserAggregate {
    @AggregateIdentifier
    private String userId;
    private String email;
    private String username;
    private String password;
    private List role;
    private Date createdDate;
    private Date birthDate;

    public UserAggregate() {}

    @CommandHandler
    public UserAggregate(CreateUserCommand createUserCommand) {

        if (createUserCommand.getEmail() == null || createUserCommand.getEmail().isBlank()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }

        if (createUserCommand.getUsername() == null || createUserCommand.getUsername().isBlank()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }

        if (createUserCommand.getPassword() == null || createUserCommand.getPassword().isBlank()) {
            throw new IllegalArgumentException("Password cannot be empty");
        }

        if (createUserCommand.getBirthDate() == null) {
            throw new IllegalArgumentException("Birth date cannot be empty");
        }

        UserCreatedEvent userCreatedEvent = new UserCreatedEvent();
        BeanUtils.copyProperties(createUserCommand, userCreatedEvent);
        AggregateLifecycle.apply(userCreatedEvent);
    }

    @EventSourcingHandler
    public void on(UserCreatedEvent userCreatedEvent) {
        System.out.println("ON AGGREGATE");
        this.userId = userCreatedEvent.getUserId();
        this.email = userCreatedEvent.getEmail();
        this.username = userCreatedEvent.getUsername();
        this.password = userCreatedEvent.getPassword();
        this.role = userCreatedEvent.getRole();
        this.createdDate = userCreatedEvent.getCreatedDate();
        this.birthDate = userCreatedEvent.getBirthDate();
    }
}
