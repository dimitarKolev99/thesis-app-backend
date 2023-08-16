package com.liebherr.hau.localapibackend.domain.core.user;

import com.liebherr.hau.localapibackend.domain.core.user.dto.RegisterUserRequest;
import com.liebherr.hau.localapibackend.domain.core.user.dto.RegisteredUserResponse;
import com.liebherr.hau.localapibackend.exception.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {

    public static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    @Autowired
    UserRepository userRepository;

    public RegisteredUserResponse addUser(RegisterUserRequest registerUserRequest) {
        User user = new User();
        user.setEmail(registerUserRequest.getEmail());

        User saveUser = userRepository.save(user);

        RegisteredUserResponse response = new RegisteredUserResponse();
        response.setUserId(saveUser.getUserId().toString());

        return response;
    }

    public User getByUserId(final UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(String.format("User not found for userId %s", userId)));
    }

}
