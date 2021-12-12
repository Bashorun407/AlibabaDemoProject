package com.alibaba.service.Exception.Exception;


import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@Data
public class ApiExceptionM {

    private final String message;
    //private final Throwable throwable;
    private final HttpStatus httpStatus;
    private final ZonedDateTime timeStamp;

    //Generating Class Constructor

    public ApiExceptionM(String message, Throwable throwable, HttpStatus httpStatus, ZonedDateTime timeStamp) {
        this.message = message;
       // this.throwable = throwable;
        this.httpStatus = httpStatus;
        this.timeStamp = timeStamp;
    }
}
