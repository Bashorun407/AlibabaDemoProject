package com.alibaba.service.Exception.Exception;

public class ApiException extends RuntimeException{

    //Generating two Constructors
    public ApiException (String message){
        super(message);
    }

    public ApiException(String message, Throwable cause) {
        super(message, cause);
    }
}
