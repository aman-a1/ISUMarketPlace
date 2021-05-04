package com309.springboot.isumarketplace.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED) //Error 401
public class UserFailedException extends Exception {
    public UserFailedException(String s) {
        super(s);
    }
}
