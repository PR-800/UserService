package com.example.userservice.command;

import com.example.userservice.command.rest.CreateUserRestModel;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserCommandService {
    @Autowired
    private final CommandGateway commandGateway;

    @Autowired
    public UserCommandService(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @RabbitListener(queues = "AddUserQueue")
    public void addUser(CreateUserRestModel model) {

        CreateUserCommand command = CreateUserCommand.builder()
                .userId(UUID.randomUUID().toString())
                .email(model.getEmail())
                .username(model.getUsername())
                .password(model.getPassword())
                .role(model.getRole())
                .createdDate(model.getCreatedDate())
                .birthDate(model.getBirthDate())
                .build();

        String result;
        try {
            result = commandGateway.sendAndWait(command);
        } catch (Exception e) {
            result = e.getLocalizedMessage();
        }

        System.out.println(" ---- Add user: " + result);
    }

    @RabbitListener(queues = "DeleteUserQueue")
    public void deleteUser(String userId) {

        System.out.println(" ---- Delete user: " + userId);
    }
}
