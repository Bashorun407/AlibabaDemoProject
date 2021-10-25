package com.alibabademo.alibaba.Exception;

public class ApiException extends RuntimeException{

    //Generating two Constructors
    public ApiException (String message){
        super(message);
    }

    public ApiException(String message, Throwable cause) {
        super(message, cause);
    }
}
