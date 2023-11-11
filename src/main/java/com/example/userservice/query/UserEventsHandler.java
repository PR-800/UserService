package com.example.userservice.query;

import com.example.userservice.core.data.UserEntity;
import com.example.userservice.core.data.UserRepository;
import com.example.userservice.core.events.UserCreatedEvent;
import com.example.userservice.core.events.UserDeletedEvent;
import com.example.userservice.core.events.UserUpdatedEvent;
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
        System.out.println("Add to db successfully");
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(event, userEntity);
        userRepository.insert(userEntity);
    }

    @EventHandler
    public void on(UserUpdatedEvent event) {
        System.out.println("Update to db successfully");
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(event, userEntity);
        userRepository.save(userEntity);
    }

    @EventHandler
    public void on(UserDeletedEvent event) {
        System.out.println("Delete from db successfully");
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(event, userEntity);
//        userRepository.deleteAll();
        userRepository.deleteById(userEntity.getUserId());
    }

}
