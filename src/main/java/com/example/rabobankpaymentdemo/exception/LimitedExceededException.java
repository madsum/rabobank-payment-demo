package com.example.rabobankpaymentdemo.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LimitedExceededException extends Exception {

    public LimitedExceededException(String errorMessage){
        super(errorMessage);
    }
}
