package com.example.userservice.command.rest;

import com.example.userservice.command.CreateUserCommand;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(value = "/users")
public class UserCommandController {
//    private final Environment env;
//    private final CommandGateway commandGateway;

    @Autowired
    private RabbitTemplate rabbitTemplate;

//    @Autowired
//    public UserCommandController(Environment env, CommandGateway commandGateway) {
//        this.env = env;
//        this.commandGateway = commandGateway;
//    }

    @PostMapping
    public String addUser(@RequestBody CreateUserRestModel model) {
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setContentType("application/json");
        rabbitTemplate.convertAndSend("Direct", "addUser", model);
        return "Create user [" + model.getUsername() + "] Successfully";
    }

    // unfinished
    @GetMapping
    public String getUser() {
        return "HTTP GET Handled ";
    }

    // unfinished
    @PutMapping("/{id}")
    public String updateUser(@PathVariable("id") String userId) {
        return "HTTP PUT Handled";
    }

    // unfinished
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") String userId) {
        rabbitTemplate.convertAndSend("Direct", "deleteUser", userId);
        return "Http DELETE Handled";
    }

}
