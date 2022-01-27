package com.example.rabobankpaymentdemo.exception;

public class InvalidCertificateException extends Exception {

    public InvalidCertificateException(String errorMessage){
        super(errorMessage);
    }
}
