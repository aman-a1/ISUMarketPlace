package com309.springboot.isumarketplace.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE) //error 406
public class RegistrationFailedExeption extends Exception {
    public RegistrationFailedExeption(String message){
        super(message);
    }
}
