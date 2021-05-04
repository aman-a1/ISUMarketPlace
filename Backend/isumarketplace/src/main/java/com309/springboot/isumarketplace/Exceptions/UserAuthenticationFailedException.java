package com309.springboot.isumarketplace.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED) //error 401
public class UserAuthenticationFailedException extends Exception{
    public UserAuthenticationFailedException(String message) { super(message); }
}

