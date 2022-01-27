package com.example.rabobankpaymentdemo.handler;

import com.example.rabobankpaymentdemo.TestData;
import com.example.rabobankpaymentdemo.exception.InvalidSignatureException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sun.security.x509.X509CertImpl;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CertificateHandlerTest {

    private CertificateHandler certificateHandler;

    @BeforeEach
    void setUp() throws InvalidSignatureException, NoSuchAlgorithmException, InvalidKeySpecException {
       certificateHandler = TestData.getCertificateHandler();
    }

    @Test
    void validVerify()  {
        var certificate = TestData.getValidX509Certificate();
        certificateHandler.setX509Certificate(certificate);
        assertTrue(certificateHandler.verify());
    }

    @Test
    void invalidVerify() {
        var certificate = new X509CertImpl();
        certificateHandler.setX509Certificate(certificate);
        assertFalse(certificateHandler.verify());
    }


    @Test
    void digestTest(){
        String expectedSha256Hex = "9f86d081884c7d659a2feaa0c55ad015a3bf4f1b2b0b822cd15d6c15b0f00a08";
        String actualSha256Hex = certificateHandler.digest("test");
        assertEquals(expectedSha256Hex, actualSha256Hex);
    }
}
