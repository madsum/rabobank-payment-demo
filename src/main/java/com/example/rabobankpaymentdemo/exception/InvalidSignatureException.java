package com.example.rabobankpaymentdemo.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class InvalidSignatureException extends Exception {

    public InvalidSignatureException(String errorMessage, Throwable err){
        super(errorMessage, err);
    }
}
