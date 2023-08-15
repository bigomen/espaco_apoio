package com.spaco_apoio.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({NotFoundException.class, InvalidData.class, UniqueException.class})
    public HashMap<String, String> RuntimeHandler(RuntimeException exception){
        HashMap<String, String> restError = new HashMap<>();
        restError.put("error", exception.getMessage());
        return restError;
    }
}
