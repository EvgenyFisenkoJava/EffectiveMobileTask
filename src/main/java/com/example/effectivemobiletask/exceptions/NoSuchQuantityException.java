package com.example.effectivemobiletask.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "wrong quantity")
public class NoSuchQuantityException extends Exception{
    public NoSuchQuantityException () {
        super();
    }
    public NoSuchQuantityException (String message) {
        super(message);
    }
}

