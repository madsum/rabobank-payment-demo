package com.example.rabobankpaymentdemo.handler;

import com.example.rabobankpaymentdemo.model.PaymentInitiationRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.bouncycastle.asn1.x500.RDN;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.asn1.x500.style.IETFUtils;
import org.bouncycastle.cert.jcajce.JcaX509CertificateHolder;

import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
public class CertificateHandler extends TppRequestHandler {

    private final String SANDBOX_TPP = "Sandbox-TPP";

    public CertificateHandler(UUID xRequestId, String certificateString, PaymentInitiationRequest paymentInitiationRequestBody, String signature) {
        super(xRequestId, certificateString, signature, paymentInitiationRequestBody);
    }

    public boolean verify()  {
        Optional<X509Certificate> x509Certificate = getX509CertificateFromCertificate(certificateString);
        AtomicBoolean cnFoundCn = new AtomicBoolean(false);
        if(x509Certificate.isEmpty()){
            return false;
        }
        try {
            X500Name x500name = new JcaX509CertificateHolder(x509Certificate.get()).getSubject();
            RDN[] cn = x500name.getRDNs(BCStyle.CN);
            Arrays.stream(cn).forEach(item -> {
                if (IETFUtils.valueToString(item.getFirst().getValue()).contains(SANDBOX_TPP)) {
                    cnFoundCn.set(true);
                }
            });
            } catch (CertificateEncodingException e) {
                log.error("Exception for certificate: {}", e.getMessage());
                cnFoundCn.set(false);
            }
        return cnFoundCn.get();
    }

    public String digest(String originalString) {
        return DigestUtils.sha256Hex(originalString);
    }
}
