package com.example.rabobankpaymentdemo.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class InvalidCertificateException extends Exception {

    public InvalidCertificateException(String errorMessage){
        super(errorMessage);    }
}
