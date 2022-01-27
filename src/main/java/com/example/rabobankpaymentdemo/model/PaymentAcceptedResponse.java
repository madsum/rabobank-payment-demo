package com.example.rabobankpaymentdemo.model;

import lombok.Data;

import java.util.UUID;

@Data
public class PaymentAcceptedResponse {

  private UUID paymentId = null;

  private TransactionStatus status = null;


}
