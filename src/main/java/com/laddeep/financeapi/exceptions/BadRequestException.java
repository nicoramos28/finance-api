package com.laddeep.financeapi.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Invalid param given")
public class BadRequestException extends AppException{

    public BadRequestException(String message){
        super(message);
    }
}
