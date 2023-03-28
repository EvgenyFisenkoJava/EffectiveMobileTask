package com.example.effectivemobiletask.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "your balance is not enough")
public class BalanceIsnotEnoughException extends Exception{
    public BalanceIsnotEnoughException() {
        super();
    }
    public BalanceIsnotEnoughException(String message) {
        super(message);
    }
}

