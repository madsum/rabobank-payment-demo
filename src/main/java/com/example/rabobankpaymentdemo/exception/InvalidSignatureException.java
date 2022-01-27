package com.example.rabobankpaymentdemo.exception;

public class InvalidSignatureException extends Exception {

    public InvalidSignatureException(String errorMessage){
        super(errorMessage);
    }
}
