package com.liebherr.hau.localapibackend.exception;

import com.liebherr.hau.localapibackend.domain.core.user.User;

public class UserNotFoundException extends NotFoundException{
    public UserNotFoundException(String message) {
        super(message, User.class);
    }
}
