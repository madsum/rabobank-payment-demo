package com.example.rabobankpaymentdemo.handler;

import com.example.rabobankpaymentdemo.model.PaymentInitiationRequest;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.ByteArrayInputStream;
import java.security.PublicKey;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Getter
@Setter
public abstract class TppRequestHandler {

    protected UUID xRequestId;

    protected String signatureCertificate;

    protected String signature;

    protected PaymentInitiationRequest paymentInitiationRequestBody;

    protected String generatedSignature;

    protected Optional<X509Certificate> x509Certificate = Optional.empty();

    protected String certificateString;

    protected String CERTIFICATE = "-----BEGIN CERTIFICATE-----\n" +
            "MIIDwjCCAqoCCQDxVbCjIKynQjANBgkqhkiG9w0BAQsFADCBojELMAkGA1UEBhMC\n" +
            "TkwxEDAOBgNVBAgMB1V0cmVjaHQxEDAOBgNVBAcMB1V0cmVjaHQxETAPBgNVBAoM\n" +
            "CFJhYm9iYW5rMRMwEQYDVQQLDApBc3Nlc3NtZW50MSIwIAYDVQQDDBlTYW5kYm94\n" +
            "LVRQUDpleGNlbGxlbnQgVFBQMSMwIQYJKoZIhvcNAQkBFhRuby1yZXBseUByYWJv\n" +
            "YmFuay5ubDAeFw0yMDAxMzAxMzIyNDlaFw0yMTAxMjkxMzIyNDlaMIGiMQswCQYD\n" +
            "VQQGEwJOTDEQMA4GA1UECAwHVXRyZWNodDEQMA4GA1UEBwwHVXRyZWNodDERMA8G\n" +
            "A1UECgwIUmFib2JhbmsxEzARBgNVBAsMCkFzc2Vzc21lbnQxIjAgBgNVBAMMGVNh\n" +
            "bmRib3gtVFBQOmV4Y2VsbGVudCBUUFAxIzAhBgkqhkiG9w0BCQEWFG5vLXJlcGx5\n" +
            "QHJhYm9iYW5rLm5sMIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAryLy\n" +
            "ouTQr1dvMT4qvek0eZsh8g0DQQLlOgBzZwx7iInxYEAgMNxCKXiZCbmWHBYqh6lp\n" +
            "Ph+BBmrnBQzB+qrSNIyd4bFhfUlQ+htK08yyL9g4nyLt0LeKuxoaVWpInrB5FRzo\n" +
            "EY5PPpcEXSObgr+pM71AvyJtQLxZbqTao4S7TRKecUm32Wwg+FWY/StSKlox3QmE\n" +
            "axEGU7aPkaQfQs4hrtuUePwKrbkQ2hQdMpvI5oXRWzTqafvEQvND+IyLvZRqf0TS\n" +
            "vIwsgtJd2tch2kqPoUwng3AmUFleJbMjFNzrWM7TH9LkKPItYtSuMTzeSe9o0SmX\n" +
            "ZFgcEBh5DnETZqIVuQIDAQABMA0GCSqGSIb3DQEBCwUAA4IBAQASFOkJiKQuL7fS\n" +
            "ErH6y5Uwj9WmmQLFnit85tjbo20jsqseTuZqLdpwBObiHxnBz7o3M73PJAXdoXkw\n" +
            "iMVykZrlUSEy7+FsNZ4iFppoFapHDbfBgM2WMV7VS6NK17e0zXcTGySSRzXsxw0y\n" +
            "EQGaOU8RJ3Rry0HWo9M/JmYFrdBPP/3sWAt/+O4th5Jyk8RajN3fHFCAoUz4rXxh\n" +
            "UZkf/9u3Q038rRBvqaA+6c0uW58XqF/QyUxuTD4er9veCniUhwIX4XBsDNxIW/rw\n" +
            "BRAxOUkG4V+XqrBb75lCyea1o/9HIaq1iIKI4Day0piMOgwPEg1wF383yd0x8hRW\n" +
            "4zxyHcER\n" +
            "-----END CERTIFICATE-----";


    public TppRequestHandler(UUID xRequestId, String certificateString, String signature, PaymentInitiationRequest paymentInitiationRequestBody) {
        this.paymentInitiationRequestBody = paymentInitiationRequestBody;
        this.certificateString = certificateString;
        this.signature = signature;
        this.xRequestId = xRequestId;
        java.security.Security.addProvider(
                new org.bouncycastle.jce.provider.BouncyCastleProvider());
    }

    protected PublicKey getPublicKeyFromCertificate(String certificateString)  {
        PublicKey publicKey = null;
        try {
            Optional<X509Certificate> x509Certificate = getX509CertificateFromCertificate(certificateString);
            publicKey = Objects.requireNonNull(x509Certificate.stream().findFirst().orElse(null)).getPublicKey();
        }catch (Exception e){
            log.error("Exception for signature: {}",
                    e.getMessage());
        }
        return publicKey;
    }

    protected Optional<X509Certificate> getX509CertificateFromCertificate(String certificateString) {
        if( x509Certificate.isEmpty()){
            ByteArrayInputStream byteArrayInputStream = null;
            try {
                CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
                byteArrayInputStream = new ByteArrayInputStream(certificateString.getBytes("UTF8"));
                x509Certificate = Optional.ofNullable((X509Certificate) certificateFactory.generateCertificate(byteArrayInputStream));
            }catch (Exception e){
                log.error("Exception for signature: {}",
                        e.getMessage());
            }
        }
        return x509Certificate;
    }

    public String digest(String originalString){
        String sha256hex = DigestUtils.sha256Hex(originalString);
        return sha256hex;
    }

    public abstract boolean verify() throws Exception;
}
