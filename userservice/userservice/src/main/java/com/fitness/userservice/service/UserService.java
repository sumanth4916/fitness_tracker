package com.fitness.userservice.service;

import com.fitness.userservice.dto.RegisterRequest;
import com.fitness.userservice.dto.UserResponse;
import com.fitness.userservice.model.User;
import com.fitness.userservice.repositery.UserReposertiry;
import jakarta.validation.Valid;
import jdk.jshell.spi.ExecutionControl;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Audited;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService {
    @Autowired
    UserReposertiry userReposertiry;
    public UserResponse register(@Valid RegisterRequest request) {

        if(userReposertiry.existsByEmail(request.getEmail()))
        {
            throw new RuntimeException("Email already exists");
        }
         User user = new User();

        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setFirstname(request.getFirstName());
        user.setLastname(request.getLastName());
        User saveduser = userReposertiry.save(user);

        UserResponse userResponse = new UserResponse();
        userResponse.setId(saveduser.getId());
        userResponse.setFirstname(saveduser.getFirstname());
        userResponse.setLastname(saveduser.getLastname());
        userResponse.setEmail(saveduser.getEmail());
        userResponse.setPassword(saveduser.getPassword());
        userResponse.setCreatedAt(saveduser.getCreatedAt());
        userResponse.setUpdatedAt(saveduser.getUpdatedAt());
        return userResponse;
    }

    public UserResponse getUserprofile(String userId) {
        User user = userReposertiry.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setFirstname(user.getFirstname());
        userResponse.setLastname(user.getLastname());
        userResponse.setEmail(user.getEmail());
        userResponse.setPassword(user.getPassword());
        userResponse.setCreatedAt(user.getCreatedAt());
        userResponse.setUpdatedAt(user.getUpdatedAt());
        return userResponse;
    }


    public Boolean existByUserId(String userId) {
        log.info("validating user id");
        return userReposertiry.existsById(userId);
    }
}
