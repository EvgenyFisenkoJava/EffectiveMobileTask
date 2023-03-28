package com.example.effectivemobiletask.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "this company or product is blocked")
public class BannedException  extends Exception{
    public BannedException () {
        super();
    }
    public BannedException (String message) {
        super(message);
    }
}
