package com.example.userservice.command;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.example.userservice.command.rest.UserRestModel;
import com.example.userservice.core.data.UserEntity;
import com.example.userservice.core.data.UserRepository;
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
    private final UserRepository userRepository;

    @Autowired
    public UserCommandService(CommandGateway commandGateway, UserRepository userRepository) {
        this.commandGateway = commandGateway;
        this.userRepository = userRepository;
    }

    @RabbitListener(queues = "AddUserQueue")
    public void addUser(UserRestModel model) {

        String password = model.getPassword();
        String bcryptHashString = BCrypt.withDefaults().hashToString(12, password.toCharArray());

        CreateUserCommand creatCommand = CreateUserCommand.builder()
                .userId(UUID.randomUUID().toString())
                .email(model.getEmail())
                .username(model.getUsername())
                .password(bcryptHashString)
                .role(model.getRole())
                .createdDate(model.getCreatedDate())
                .birthDate(model.getBirthDate())
                .build();

        try {
            commandGateway.sendAndWait(creatCommand);
        } catch (Exception e) {
            e.getLocalizedMessage();
        }

        System.out.println(" ---- Add user: " + model.getUsername());
    }

    @RabbitListener(queues = "UpdateUserQueue")
    public void updateUser(UserRestModel model) {

        String password = model.getPassword();
        String bcryptHashString = BCrypt.withDefaults().hashToString(12, password.toCharArray());

        UpdateUserCommand updateCommand = UpdateUserCommand.builder()
                .userId(model.getUserId())
                .email(model.getEmail())
                .username(model.getUsername())
                .password(bcryptHashString)
                .role(model.getRole())
                .createdDate(model.getCreatedDate())
                .birthDate(model.getBirthDate())
                .build();

            try {
                commandGateway.sendAndWait(updateCommand);
            } catch (Exception e) {
                e.getLocalizedMessage();
            }

        System.out.println(" ---- Update user: " + model.getUserId());
    }

    @RabbitListener(queues = "DeleteUserQueue")
    public void deleteUser(String userId) {

        UserEntity userEntity = userRepository.findUserByUserId(userId);
        System.out.println("userEntity: " + userEntity);

        if (userEntity != null) {
            DeleteUserCommand deleteCommand = DeleteUserCommand.builder()
                    .userId(userEntity.getUserId())
                    .email(userEntity.getEmail())
                    .username(userEntity.getUsername())
                    .password(userEntity.getPassword())
                    .role(userEntity.getRole())
                    .createdDate(userEntity.getCreatedDate())
                    .birthDate(userEntity.getBirthDate())
                    .build();

            try {
                commandGateway.sendAndWait(deleteCommand);
            } catch (Exception e) {
                e.getLocalizedMessage();
            }
            System.out.println(" ---- Delete user: " + userEntity.getUserId());
        }
        else {
            System.out.println(" ---- Delete user: Not found");
        }

    }
}
