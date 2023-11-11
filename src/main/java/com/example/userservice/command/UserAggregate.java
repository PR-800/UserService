package com.example.userservice.command;

import com.example.userservice.core.events.UserCreatedEvent;
import com.example.userservice.core.events.UserDeletedEvent;
import com.example.userservice.core.events.UserUpdatedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import java.util.Date;
import java.util.List;
import java.util.UUID;

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
        System.out.println("ADD USER");

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
        System.out.println("ADD USER ON AGGREGATE");
        this.userId = userCreatedEvent.getUserId();
        this.email = userCreatedEvent.getEmail();
        this.username = userCreatedEvent.getUsername();
        this.password = userCreatedEvent.getPassword();
        this.role = userCreatedEvent.getRole();
        this.createdDate = userCreatedEvent.getCreatedDate();
        this.birthDate = userCreatedEvent.getBirthDate();
    }

    @CommandHandler
    public UserAggregate(UpdateUserCommand updateUserCommand) {
        System.out.println("UPDATE USER");

        if (updateUserCommand.getEmail() == null || updateUserCommand.getEmail().isBlank()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }

        if (updateUserCommand.getUsername() == null || updateUserCommand.getUsername().isBlank()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }

        if (updateUserCommand.getPassword() == null || updateUserCommand.getPassword().isBlank()) {
            throw new IllegalArgumentException("Password cannot be empty");
        }

        if (updateUserCommand.getBirthDate() == null) {
            throw new IllegalArgumentException("Birth date cannot be empty");
        }

        UserUpdatedEvent userUpdatedEvent = new UserUpdatedEvent();
        BeanUtils.copyProperties(updateUserCommand, userUpdatedEvent);
        AggregateLifecycle.apply(userUpdatedEvent);
    }

    @EventSourcingHandler
    public void on(UserUpdatedEvent userUpdatedEvent) {
        System.out.println("UPDATE USER ON AGGREGATE");
        this.userId = UUID.randomUUID().toString();
        this.email = userUpdatedEvent.getEmail();
        this.username = userUpdatedEvent.getUsername();
        this.password = userUpdatedEvent.getPassword();
        this.role = userUpdatedEvent.getRole();
        this.createdDate = userUpdatedEvent.getCreatedDate();
        this.birthDate = userUpdatedEvent.getBirthDate();
    }

    @CommandHandler
    public UserAggregate(DeleteUserCommand deleteUserCommand) {
        System.out.println("DELETE USER");
        UserDeletedEvent userDeletedEvent = new UserDeletedEvent();
        BeanUtils.copyProperties(deleteUserCommand, userDeletedEvent);
        AggregateLifecycle.apply(userDeletedEvent);
    }

    @EventSourcingHandler
    public void on(UserDeletedEvent userDeletedEvent) {
        System.out.println("DELETE USER ON AGGREGATE");
        this.userId = UUID.randomUUID().toString();
        this.email = userDeletedEvent.getEmail();
        this.username = userDeletedEvent.getUsername();
        this.password = userDeletedEvent.getPassword();
        this.role = userDeletedEvent.getRole();
        this.createdDate = userDeletedEvent.getCreatedDate();
        this.birthDate = userDeletedEvent.getBirthDate();
    }
}
