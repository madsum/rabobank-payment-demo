package com.example.rabobankpaymentdemo.handler;

import com.example.rabobankpaymentdemo.exception.InvalidSignatureException;
import com.example.rabobankpaymentdemo.model.PaymentInitiationRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import sun.security.x509.X500Name;

import javax.security.auth.x500.X500Principal;
import java.io.IOException;
import java.util.UUID;

@Slf4j
public class CertificateHandler extends TppRequestHandler {

    public CertificateHandler(UUID xRequestId, String certificateString, PaymentInitiationRequest paymentInitiationRequestBody, String signature) {
        super(xRequestId, certificateString, paymentInitiationRequestBody, signature);
    }

    public boolean verify() throws IOException, InvalidSignatureException {
        X500Principal principal = getX509Certificate().getSubjectX500Principal();
        if (principal != null) {
            X500Name x500name = new X500Name(principal.getName());
            if (x500name != null) {
                String commonName = x500name.getCommonName();
                if (commonName.contains("Sandbox-TPP")) {
                    return true;
                }
            }
        }
        return false;
    }

    public String digest(String originalString) {
        String sha256hex = DigestUtils.sha256Hex(originalString);
        return sha256hex;
    }

}
