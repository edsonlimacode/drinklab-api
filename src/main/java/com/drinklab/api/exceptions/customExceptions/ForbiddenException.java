package com.drinklab.api.exceptions.customExceptions;

import org.springframework.security.authorization.AuthorizationDeniedException;

public class ForbiddenException extends AuthorizationDeniedException {

    public ForbiddenException(String message) {
        super(message);
    }
}
