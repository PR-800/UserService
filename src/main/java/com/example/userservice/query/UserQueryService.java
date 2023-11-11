package com.example.userservice.query;

import com.example.userservice.query.rest.FindUserByIdQuery;
import com.example.userservice.query.rest.FindUserQuery;
import com.example.userservice.query.rest.UserRestModel;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class UserQueryService {

    @Autowired
    private QueryGateway queryGateway;

    public UserQueryService(QueryGateway queryGateway) {
        this.queryGateway = queryGateway;
    }


    @RabbitListener(queues = "GetUsersQueue")
    public List<UserRestModel> getUsers() {
        FindUserQuery findUserQuery = new FindUserQuery();
        return queryGateway.query(
                findUserQuery,
                ResponseTypes.multipleInstancesOf(UserRestModel.class)
        ).join();
    }

    @RabbitListener(queues = "FindUserQueue")
    public UserRestModel findUser(String userId) {
        FindUserByIdQuery findUserByIdQuery = new FindUserByIdQuery(userId);
        return queryGateway.query(
                findUserByIdQuery,
                ResponseTypes.instanceOf(UserRestModel.class)
        ).join();
    }
}
