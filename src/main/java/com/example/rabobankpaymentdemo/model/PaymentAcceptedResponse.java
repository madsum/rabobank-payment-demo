package com.example.rabobankpaymentdemo.model;

import lombok.Data;

import java.util.Objects;
import java.util.UUID;



@Data
public class PaymentAcceptedResponse {

  private UUID paymentId = null;

  private TransactionStatus status = null;

  public PaymentAcceptedResponse paymentId(UUID paymentId) {
    this.paymentId = paymentId;
    return this;
  }

  public PaymentAcceptedResponse status(TransactionStatus status) {
    this.status = status;
    return this;
  }

  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PaymentAcceptedResponse paymentAcceptedResponse = (PaymentAcceptedResponse) o;
    return Objects.equals(this.paymentId, paymentAcceptedResponse.paymentId) &&
        Objects.equals(this.status, paymentAcceptedResponse.status);
  }

  public int hashCode() {
    return Objects.hash(paymentId, status);
  }


  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("PaymentAcceptedResponse {\n");
    
    sb.append("    paymentId: ").append(toIndentedString(paymentId)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

}
