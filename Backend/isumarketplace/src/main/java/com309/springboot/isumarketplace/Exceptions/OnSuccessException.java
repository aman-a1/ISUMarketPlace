package com309.springboot.isumarketplace.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.OK) //Code 200
public class OnSuccessException extends Exception {
    public OnSuccessException(String message){
        super(message);
    }
}
