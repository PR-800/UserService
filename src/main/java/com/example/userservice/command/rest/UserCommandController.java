package com.example.userservice.command.rest;

import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/users")
public class UserCommandController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostMapping
    public String addUser(@RequestBody UserRestModel model) {
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setContentType("application/json");
        rabbitTemplate.convertAndSend("Direct", "addUser", model);
        return "Create user [" + model.getUsername() + "] Successfully";
    }

    @PutMapping
    public String updateUser(@RequestBody UserRestModel model) {
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setContentType("application/json");
        rabbitTemplate.convertAndSend("Direct", "updateUser", model);
        return "Update user [" + model.getUsername() + "] Successfully";
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") String userId) {
        rabbitTemplate.convertAndSend("Direct", "deleteUser", userId);
        return "Delete user id [" + userId + "] Successfully";
    }

}
