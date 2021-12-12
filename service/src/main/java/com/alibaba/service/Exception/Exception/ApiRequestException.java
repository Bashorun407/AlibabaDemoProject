package com.alibaba.service.Exception.Exception;

public class ApiRequestException extends RuntimeException{

    //Generating the class Constructor

    public ApiRequestException(String message) {
        super(message);
    }

    public ApiRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
