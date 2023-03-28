package com.example.effectivemobiletask.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNAUTHORIZED, reason = "not authorized")
public class NotAuthorizedException extends Exception{
    public NotAuthorizedException () {
        super();
    }
    public NotAuthorizedException (String message) {
        super(message);
    }
}
