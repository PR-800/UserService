package com.example.userservice.core.data;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<UserEntity, String>{
    @Query(value = "{ 'userId' : ?0 }")
    public UserEntity findUserByUserId(String userId);
}
