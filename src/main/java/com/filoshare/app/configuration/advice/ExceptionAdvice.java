package com.filoshare.app.configuration.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionAdvice {
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> exceptionResult(Exception exception) {
        return new ResponseEntity<String>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
