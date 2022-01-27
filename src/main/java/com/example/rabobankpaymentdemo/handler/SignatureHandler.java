package com.example.rabobankpaymentdemo.handler;

import com.example.rabobankpaymentdemo.model.PaymentInitiationRequest;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.util.Base64;
import java.util.UUID;

@Slf4j
public class SignatureHandler extends TppRequestHandler {

    public SignatureHandler(UUID xRequestId, String certificateString, PaymentInitiationRequest paymentInitiationRequestBody, String signature) {
        super(xRequestId, certificateString, paymentInitiationRequestBody, signature);
    }


    public boolean verify(){
        boolean isVerified = false;
        try {
            PublicKey publicKey  =  getPublicKeyFromCertificate(CERTIFICATE);
            String sha256Encoded = digest(paymentInitiationRequestBody.toString());
            sha256Encoded = xRequestId.toString().concat(sha256Encoded);
            Signature publicSignature = Signature.getInstance("SHA256withRSA");
            publicSignature.initVerify(publicKey);
            publicSignature.update(sha256Encoded.getBytes(StandardCharsets.UTF_8));

            byte[] signatureBytes = Base64.getDecoder().decode(signature);
            log.info("Base64 signature : "+signatureBytes.toString());
            isVerified = publicSignature.verify(signatureBytes);
        }catch (Exception e){
            log.error("Exception for signature: {}",
                    e.getMessage());
        }
        return isVerified;
    }

    public String generateSignature(String plainText, PrivateKey privateKey){
        byte[] signature = {'0'};
        try {
            Signature privateSignature = Signature.getInstance("SHA256withRSA");
            privateSignature.initSign(privateKey);
            privateSignature.update(plainText.getBytes(StandardCharsets.UTF_8));
            signature = privateSignature.sign();
        } catch (Exception e){
            log.error("Exception for signature: {}",
                    e.getMessage());
        }
        return  Base64.getEncoder().encodeToString(signature);
    }

}
