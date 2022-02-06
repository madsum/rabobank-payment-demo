package com.example.rabobankpaymentdemo.model;

public enum ErrorReasonCode {
  UNKNOWN_CERTIFICATE("UNKNOWN_CERTIFICATE"),
  INVALID_SIGNATURE("INVALID_SIGNATURE"),
  INVALID_REQUEST("INVALID_REQUEST"),
  LIMIT_EXCEEDED("LIMIT_EXCEEDED"),
  GENERAL_ERROR("GENERAL_ERROR"),
  VALID_IBAN("VALID_IBAN"),
  PAYMENT_ACCEPT("PAYMENT_ACCEPT");

  private final String value;

  ErrorReasonCode(String value) {
    this.value = value;
  }

  public String toString() {
    return String.valueOf(value);
  }

}
