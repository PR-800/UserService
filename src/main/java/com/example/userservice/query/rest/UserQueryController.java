package com.example.userservice.query.rest;

import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/getUsers")
public class UserQueryController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private final QueryGateway queryGateway;

    public UserQueryController(QueryGateway queryGateway) {
        this.queryGateway = queryGateway;
    }

    @GetMapping
    public List<UserRestModel> getUsers() {
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setContentType("application/json");
        Object users = rabbitTemplate.convertSendAndReceive("Direct", "getUsers", "");
        return (List<UserRestModel>) users;
//        FindUserQuery findUserQuery = new FindUserQuery();
//        return queryGateway.query(
//                findUserQuery,
//                ResponseTypes.multipleInstancesOf(UserRestModel.class)
//        ).join();
    }

    @GetMapping("/{id}")
    public UserRestModel findUser(@PathVariable("id") String userId) {
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setContentType("application/json");
        Object users = rabbitTemplate.convertSendAndReceive("Direct", "findUser", userId);
        return (UserRestModel) users;
    }
}
