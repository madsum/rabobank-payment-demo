package com.example.rabobankpaymentdemo.controller;

import com.example.rabobankpaymentdemo.exception.InvalidCertificateException;
import com.example.rabobankpaymentdemo.handler.CertificateHandler;
import com.example.rabobankpaymentdemo.handler.IbanHandler;
import com.example.rabobankpaymentdemo.handler.ResponseHandler;
import com.example.rabobankpaymentdemo.handler.SignatureHandler;
import com.example.rabobankpaymentdemo.model.ErrorReasonCode;
import com.example.rabobankpaymentdemo.model.PaymentAcceptedResponse;
import com.example.rabobankpaymentdemo.model.PaymentInitiationRequest;
import com.example.rabobankpaymentdemo.util.PaymentUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static com.example.rabobankpaymentdemo.model.ErrorReasonCode.INVALID_SIGNATURE;
import static com.example.rabobankpaymentdemo.model.ErrorReasonCode.LIMIT_EXCEEDED;

@Slf4j
@RestController
@RequestMapping(PaymentIInitController.PAYMENT_INITIATE_VERSION)
public class PaymentIInitController {

    public static final String PAYMENT_INITIATE_VERSION = "/v1.0.0";
    public static final String INITIATE_PAYMENT = "/initiate-payment";
    public static final String GET_SIGNATURE = "/get-signature";

    @PostMapping(value = INITIATE_PAYMENT)
    public ResponseEntity<PaymentAcceptedResponse> initiatePayment(@RequestHeader(value="X-Request-Id", required=true) UUID xRequestId,
                                                                   @RequestHeader(value="Signature-Certificate", required=true) String signatureCertificate,
                                                                   @RequestHeader(value="Signature", required=true) String signature,
                                                                   @RequestBody PaymentInitiationRequest body) throws InvalidCertificateException {
        ErrorReasonCode errorReasonCode = ErrorReasonCode.GENERAL_ERROR;
        CertificateHandler certificateHandler  = new CertificateHandler(xRequestId, signatureCertificate, body, signature);
        if(!certificateHandler.verify()){
            errorReasonCode = ErrorReasonCode.UNKNOWN_CERTIFICATE;
            log.error("Error: {}",errorReasonCode.name());
            return ResponseHandler.buildResponse(errorReasonCode);
        }

        SignatureHandler signatureHandler = new SignatureHandler(xRequestId, signatureCertificate, body, signature);
        if(!signatureHandler.verify()){
            errorReasonCode = INVALID_SIGNATURE;
            log.error("Error: {}",errorReasonCode.name());
            return ResponseHandler.buildResponse(errorReasonCode);
        }

        IbanHandler ibanHandler = new IbanHandler(body);
        if(!ibanHandler.verify()){
            errorReasonCode = LIMIT_EXCEEDED;
            log.error("Error: {}",errorReasonCode.name());
            return ResponseHandler.buildResponse(errorReasonCode);
        }
        return ResponseHandler.buildResponse(errorReasonCode);
    }

    @PostMapping(value = GET_SIGNATURE)
    public String getSignature(@RequestHeader(value="X-Request-Id", required=true) UUID xRequestId, @RequestBody PaymentInitiationRequest body){
        String sha256Encoded = DigestUtils.sha256Hex(body.toString());
        sha256Encoded = xRequestId.toString().concat(sha256Encoded);
        String signature = PaymentUtil.generateSignature(sha256Encoded);
        return signature;
    }
}
