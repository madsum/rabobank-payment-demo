package com.example.rabobankpaymentdemo.handler;

import com.example.rabobankpaymentdemo.exception.InvalidCertificateException;
import com.example.rabobankpaymentdemo.model.PaymentInitiationRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import sun.security.x509.X500Name;

import javax.security.auth.x500.X500Principal;
import java.io.IOException;
import java.security.cert.X509Certificate;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Slf4j
public class CertificateHandler extends TppRequestHandler {

    public CertificateHandler(UUID xRequestId, String certificateString, PaymentInitiationRequest paymentInitiationRequestBody, String signature) {
        super(xRequestId, certificateString, signature, paymentInitiationRequestBody);
    }

    public boolean verify() throws InvalidCertificateException {
        Optional<X509Certificate> x509Certificate = getX509CertificateFromCertificate(certificateString);
        if(x509Certificate.isEmpty()){
            return false;
        }
        X500Principal principal  = Objects.requireNonNull(x509Certificate.stream()
                                    .findFirst()
                                    .orElse(null))
                                    .getSubjectX500Principal();
        if (principal != null) {
            try {
                X500Name x500name = new X500Name(principal.getName());
                String commonName = x500name.getCommonName();
                if (commonName.contains("Sandbox-TPP")) {
                    return true;
                }
            } catch (IOException e) {
                log.error("Exception for certificate: {}",
                        e.getMessage());
                throw new InvalidCertificateException("Invalid certificate");
            }
        }
        return false;
    }

    public String digest(String originalString) {
        return DigestUtils.sha256Hex(originalString);
    }

}
