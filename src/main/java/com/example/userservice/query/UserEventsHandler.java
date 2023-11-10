package com.example.userservice.query;

import com.example.userservice.core.data.UserEntity;
import com.example.userservice.core.data.UserRepository;
import com.example.userservice.core.events.UserCreatedEvent;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class UserEventsHandler {
    private final UserRepository userRepository;

    public UserEventsHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @EventHandler
    public void on(UserCreatedEvent event) {
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(event, userEntity);
        System.out.println(userEntity);
        userRepository.save(userEntity);
    }

}
