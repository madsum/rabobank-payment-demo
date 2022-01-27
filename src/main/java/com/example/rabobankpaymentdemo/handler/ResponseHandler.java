package com.example.rabobankpaymentdemo.handler;

import com.example.rabobankpaymentdemo.model.ErrorReasonCode;
import com.example.rabobankpaymentdemo.model.PaymentAcceptedResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static com.example.rabobankpaymentdemo.model.TransactionStatus.ACCEPTED;
import static com.example.rabobankpaymentdemo.model.TransactionStatus.REJECTED;

public class ResponseHandler {

    public static ResponseEntity<PaymentAcceptedResponse> buildResponse(ErrorReasonCode errorReasonCode){

        HttpHeaders responseHeaders = new HttpHeaders();
        PaymentAcceptedResponse paymentAcceptedResponse = new PaymentAcceptedResponse();
        paymentAcceptedResponse.setPaymentId(UUID.randomUUID());
        paymentAcceptedResponse.setStatus(ACCEPTED);
        ResponseEntity<PaymentAcceptedResponse> response;

        switch (errorReasonCode){
            case UNKNOWN_CERTIFICATE:
                responseHeaders.set("UNKNOWN_CERTIFICATE", ErrorReasonCode.UNKNOWN_CERTIFICATE.name());
                paymentAcceptedResponse.setStatus(REJECTED);
                response = ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .headers(responseHeaders)
                        .body(paymentAcceptedResponse);
                break;

            case INVALID_SIGNATURE:
               responseHeaders.set("UNKNOWN_CERTIFICATE", ErrorReasonCode.INVALID_SIGNATURE.name());
                paymentAcceptedResponse.setStatus(REJECTED);
                response = ResponseEntity.status(HttpStatus.BAD_REQUEST)
                          .headers(responseHeaders)
                          .body(paymentAcceptedResponse);
               break;

            case LIMIT_EXCEEDED:
                responseHeaders.set("Unprocessable entity", HttpStatus.UNPROCESSABLE_ENTITY.name());
                paymentAcceptedResponse.setStatus(REJECTED);
                response = ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                        .headers(responseHeaders)
                        .body(paymentAcceptedResponse);
                break;

            default:
                responseHeaders.set("HttpHeaders.ACCEPT", HttpHeaders.ACCEPT);
                paymentAcceptedResponse.setStatus(ACCEPTED);
                response = ResponseEntity.status(HttpStatus.CREATED)
                        .headers(responseHeaders)
                        .body(paymentAcceptedResponse);
        }
        return response;
    }

}
