package com.example.rabobankpaymentdemo.handler;

import com.example.rabobankpaymentdemo.TestData;
import com.example.rabobankpaymentdemo.exception.InvalidSignatureException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class SignatureHandlerTest {

    private SignatureHandler signatureHandler;

    @BeforeEach
    void setUp() throws InvalidSignatureException, NoSuchAlgorithmException, InvalidKeySpecException {
       signatureHandler = TestData.geeSignatureHandler();
    }

    @Test
    void testVerifyValidSignature() {
        assertTrue(signatureHandler.verify());
    }

    @Test
    void testVerifyInvalidSignature() {
        signatureHandler.setSignature("Invalid");
        assertFalse(signatureHandler.verify());
    }
}
