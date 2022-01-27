package com.example.rabobankpaymentdemo.model;

import lombok.Data;

@Data
public class PaymentInitiationRequest {

  private String debtorIBAN = null;

  private String creditorIBAN = null;

  private String amount = null;

  private String currency = "EUR";

  private String endToEndId = null;
}
