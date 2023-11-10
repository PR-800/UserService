package com.example.userservice.query;

import com.example.userservice.core.data.UserEntity;
import com.example.userservice.core.data.UserRepository;
import com.example.userservice.query.rest.FindUserQuery;
import com.example.userservice.query.rest.UserRestModel;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserQueryHandler {
    private final UserRepository userRepository;

    public UserQueryHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @QueryHandler
    public List<UserRestModel> findUsers(FindUserQuery query) {
        List<UserRestModel> userRest = new ArrayList<>();
        List<UserEntity> storedUsers = userRepository.findAll();
        for (UserEntity userEntity : storedUsers) {
            UserRestModel userRestModel = new UserRestModel();
            BeanUtils.copyProperties(userEntity, userRestModel);
            userRest.add(userRestModel);
        }
        return userRest;
    }
}
