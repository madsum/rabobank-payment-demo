package com.example.rabobankpaymentdemo.handler;

import com.example.rabobankpaymentdemo.model.PaymentInitiationRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import sun.security.x509.X500Name;

import javax.security.auth.x500.X500Principal;
import java.io.IOException;
import java.security.cert.X509Certificate;
import java.util.UUID;

@Slf4j
public class CertificateHandler extends TppRequestHandler {

    public CertificateHandler(UUID xRequestId, String certificateString, PaymentInitiationRequest paymentInitiationRequestBody, String signature) {
        super(xRequestId, certificateString, paymentInitiationRequestBody, signature);
    }

    public boolean verify()  {
        X509Certificate x509Certificate = getX509Certificate();
        if(x509Certificate == null){
            return false;
        }
        X500Principal principal = x509Certificate.getSubjectX500Principal();
        if (principal != null) {
            X500Name x500name;
            try {
                x500name = new X500Name(principal.getName());
                if (x500name != null) {
                    String commonName = x500name.getCommonName();
                    if (commonName.contains("Sandbox-TPP")) {
                        return true;
                    }
                }
            } catch (IOException e) {
                log.error("Exception for certificate: {}",
                        e.getMessage());
            }

        }
        return false;
    }

    public String digest(String originalString) {
        String sha256hex = DigestUtils.sha256Hex(originalString);
        return sha256hex;
    }

}
