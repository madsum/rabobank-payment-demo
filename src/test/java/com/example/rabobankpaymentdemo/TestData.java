package com.example.rabobankpaymentdemo;

import com.example.rabobankpaymentdemo.handler.CertificateHandler;
import com.example.rabobankpaymentdemo.handler.SignatureHandler;
import com.example.rabobankpaymentdemo.model.PaymentInitiationRequest;
import com.example.rabobankpaymentdemo.util.PaymentUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.UUID;

public class TestData {

    public static final String CERTIFICATE = "-----BEGIN CERTIFICATE-----\n" +
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

    public static final String SIGNATURE = "D0YnSFErjHF8+gpuZFxAoB7v15443qKWTaekhMZMMoNiVxTRROh4Hy5F7a7JYGig/n7kGts5D6GakdcIdZuOMbkOPknLahjSWILpbkoqMke1ZAFXHZhJ+T4+SDCP7om3fHLhxhrxIG/J5WiEzYm9ywsAGM6u7nWVhg0IgMLNhcJLIEHQVUQvKYfXPOsXJz10iMWGXF3mBFxRIprvnJgJaXq1dmN8cvqQRYpYyOz5poYNr61R/YPdMxzm/O7T/6nmxegXynAOzXEh4E0/aFxzppaeFGyWCejaW0BxRuTlwofcC76S3m4xrHqawX/FZ4OAtgTARmcTM0FPPEJzgBBOww==";
    public static final String RABO_SIGNATURE = "AlFr/WbYiekHmbB6XdEO/7ghKd0n6q/bapENAYsL86KoYHqa4eP34xfH9icpQRmTpH0qOkt1vfUPWnaqu+vHBWx/gJXiuVlhayxLZD2w41q8ITkoj4oRLn2U1q8cLbjUtjzFWX9TgiQw1iY0ezpFqyDLPU7+ZzO01JI+yspn2gtto0XUm5KuxUPK24+xHD6R1UZSCSJKXY1QsKQfJ+gjzEjrtGvmASx1SUrpmyzVmf4qLwFB1ViRZmDZFtHIuuUVBBb835dCs2W+d7a+icGOCtGQbFcHvW0FODibnY5qq8v5w/P9i9PSarDaGgYb+1pMSnF3p8FsHAjk3Wccg2a1GQ==";
    public static final String SIGNATURE_422 = "Hu044/rzsfD/6VVrB2FHnC+BzIychwRckG24YuxR+2tRUQtrOr7WmuXuAQ8sw8ARmdFhSEme2XxQG9GRbBZ21cZK/Je9cbpWdzVuQNsNR1q5xiJClBot7pcH3PN/P8LhFiqezCr6iVULX30cwHlsrMHUjE4s74mWuy0BL3Th62sS2y5eDEU8q1M1a9s8cWkpaOjsiLqlLiQFKCql9ILhZWbKWs4tYBnJ+9LjIpO1KKEjZgcGMD7DUb0l7KF2I+DUElG++HmBfAymjRIS8Sr2goMBr0JnFQ21wj9P0gp5OlDLJ9riPt9K4oOGZXEWXgBzCB3oSmdLOQNf8m3bEvvJnA==";

    public static final UUID uuid = UUID.fromString("29318e25-cebd-498c-888a-f77672f66449");

    public static  X509Certificate getValidX509Certificate(){
        X509Certificate certificate = null;
        try {
            CertificateFactory fact = CertificateFactory.getInstance("X.509");
            ByteArrayInputStream is = new ByteArrayInputStream(CERTIFICATE.getBytes(StandardCharsets.UTF_8));
            certificate = (X509Certificate) fact.generateCertificate(is);
        }catch (Exception ignore){
        }
        return certificate;
    }

    public static CertificateHandler getCertificateHandler() {
        UUID xRequestId = UUID.fromString("29318e25-cebd-498c-888a-f77672f66449");
        PaymentInitiationRequest paymentInitiationRequest = getValidPaymentInitiationRequest();
        String sha256Encoded = DigestUtils.sha256Hex(paymentInitiationRequest.toString());
        sha256Encoded = xRequestId.toString().concat(sha256Encoded);
        String signature = PaymentUtil.generateSignature(sha256Encoded);
        return new CertificateHandler(xRequestId, CERTIFICATE, paymentInitiationRequest, signature);
    }

    public static SignatureHandler geeSignatureHandler(){
        UUID xRequestId = UUID.fromString("29318e25-cebd-498c-888a-f77672f66449");
        PaymentInitiationRequest paymentInitiationRequest = getValidPaymentInitiationRequest();
        String sha256Encoded = DigestUtils.sha256Hex(paymentInitiationRequest.toString());
        sha256Encoded = xRequestId.toString().concat(sha256Encoded);
        String signature = PaymentUtil.generateSignature(sha256Encoded);
        return new SignatureHandler(xRequestId, CERTIFICATE, paymentInitiationRequest, signature);
    }

    public static PaymentInitiationRequest getValidPaymentInitiationRequest(){
        PaymentInitiationRequest paymentInitiationRequest = new PaymentInitiationRequest();
        paymentInitiationRequest.setCreditorIBAN("NL94ABNA1008270121");
        paymentInitiationRequest.setDebtorIBAN("NL02RABO7134384551");
        paymentInitiationRequest.setAmount("100");
        paymentInitiationRequest.setCurrency("EUR");
        return paymentInitiationRequest;
    }

    public static String getValidPaymentInitiationRequestJson() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(getValidPaymentInitiationRequest());
    }

    public static PaymentInitiationRequest getInvalidPaymentInitiationRequest(){
        PaymentInitiationRequest paymentInitiationRequest = new PaymentInitiationRequest();
        paymentInitiationRequest.setCreditorIBAN("NL94ABNA1008270121");
        paymentInitiationRequest.setDebtorIBAN("NL02RABO7134384113");
        paymentInitiationRequest.setAmount("500");
        paymentInitiationRequest.setCurrency("EUR");
        return paymentInitiationRequest;
    }

    public static String getInvalidPaymentInitiationRequestJson() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(getInvalidPaymentInitiationRequest());
    }
}
