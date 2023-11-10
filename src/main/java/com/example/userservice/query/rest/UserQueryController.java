package com.example.userservice.query.rest;

import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserQueryController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private final QueryGateway queryGateway;

    public UserQueryController(QueryGateway queryGateway) {
        this.queryGateway = queryGateway;
    }

    @GetMapping
    public List<UserRestModel> getUsers() {
        FindUserQuery findUserQuery = new FindUserQuery();
        return queryGateway.query(
                findUserQuery,
                ResponseTypes.multipleInstancesOf(UserRestModel.class)
        ).join();
    }
}
