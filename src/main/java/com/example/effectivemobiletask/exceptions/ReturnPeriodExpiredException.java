package com.example.effectivemobiletask.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "return period is expired")
public class ReturnPeriodExpiredException extends Exception{
    public ReturnPeriodExpiredException () {
        super();
    }
    public ReturnPeriodExpiredException (String message) {
        super(message);
    }
}
