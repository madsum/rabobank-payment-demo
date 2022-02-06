package com.example.rabobankpaymentdemo.model;

import lombok.Data;

@Data
public class PaymentInitiationRequest {

  private String debtorIBAN;

  private String creditorIBAN;

  private String amount;

  private String currency = "EUR";

  private String endToEndId;
}
