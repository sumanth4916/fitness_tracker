package com.fitness.userservice.service;

import com.fitness.userservice.dto.RegisterRequest;
import com.fitness.userservice.dto.UserResponse;
import com.fitness.userservice.model.User;
import com.fitness.userservice.repositery.UserReposertiry;
import jakarta.validation.Valid;
import org.hibernate.annotations.Audited;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserReposertiry userReposertiry;
    public UserResponse register(@Valid RegisterRequest request) {

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
        return userResponse;



    }
}
