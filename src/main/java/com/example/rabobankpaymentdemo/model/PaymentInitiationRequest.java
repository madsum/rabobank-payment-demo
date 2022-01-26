package com.example.rabobankpaymentdemo.model;

import lombok.Data;

@Data
public class PaymentInitiationRequest {

  private String debtorIBAN = null;

  private String creditorIBAN = null;

  private String amount = null;

  private String currency = "EUR";

  private String endToEndId = null;

/*
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("PaymentInitiationRequest {\n");
    
    sb.append("    debtorIBAN: ").append(toIndentedString(debtorIBAN)).append("\n");
    sb.append("    creditorIBAN: ").append(toIndentedString(creditorIBAN)).append("\n");
    sb.append("    amount: ").append(toIndentedString(amount)).append("\n");
    sb.append("    currency: ").append(toIndentedString(currency)).append("\n");
    sb.append("    endToEndId: ").append(toIndentedString(endToEndId)).append("\n");
    sb.append("}");
    return sb.toString();
  }
*/

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
