package com309.springboot.isumarketplace.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST) //Code 400
public class OnFailedException extends Exception{
    public OnFailedException(String message) { super(message);}
}
