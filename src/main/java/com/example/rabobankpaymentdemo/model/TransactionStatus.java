package com.example.rabobankpaymentdemo.model;

public enum TransactionStatus {
    ACCEPTED("Accepted"),
    REJECTED("Rejected");

    private String value;

    TransactionStatus(String value) {
        this.value = value;
    }

    public String toString() {
        return String.valueOf(value);
    }

}
