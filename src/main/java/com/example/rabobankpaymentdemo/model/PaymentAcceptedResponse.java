package com.example.rabobankpaymentdemo.model;

import lombok.Data;

import java.util.UUID;

@Data
public class PaymentAcceptedResponse {
  private UUID paymentId;
  private TransactionStatus status;
}
