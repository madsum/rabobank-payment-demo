package com.example.rabobankpaymentdemo.exception;

public class LimitedExceededException extends Exception {

    public LimitedExceededException(String errorMessage){
        super(errorMessage);
    }
}
