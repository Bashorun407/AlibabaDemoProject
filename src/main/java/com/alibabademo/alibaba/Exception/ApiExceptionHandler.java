package com.alibabademo.alibaba.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiExceptionHandler {

    //Method to handle specific Exception
    @ExceptionHandler
    public ResponseEntity<Object> exceptionHandler(ApiRequestException e){

        HttpStatus badRequest = HttpStatus.BAD_REQUEST;

        ApiExceptionM apiExceptionM = new ApiExceptionM(
                e.getMessage(),
                e,
                badRequest,
                ZonedDateTime.now(ZoneId.of("Z"))
        );

        return new ResponseEntity<>(apiExceptionM, badRequest);
    }



//    //I wrote this for sake practice
//    @ExceptionHandler
//    public ResponseEntity<Object> exceptionHandler2(ApiException e){
//
//        HttpStatus conflict = HttpStatus.CONFLICT;
//
//        ApiRequestException apiRequestException = new ApiRequestException(
//                e.getMessage(),
//                e,
//                conflict,
//                ZonedDateTime.now(ZoneId.of("Y"))
//        );
//
//        return new ResponseEntity<>(apiRequestException, conflict);
//    }

}
