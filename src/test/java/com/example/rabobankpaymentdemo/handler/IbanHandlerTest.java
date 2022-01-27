package com.example.rabobankpaymentdemo.handler;

import com.example.rabobankpaymentdemo.TestData;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class IbanHandlerTest {

    @Test
    void testVerifyValidIBAN() {
        IbanHandler ibanHandler = new IbanHandler(TestData.getValidPaymentInitiationRequest());
        assertTrue(ibanHandler.verify());
    }

    @Test
    void testVerifyInvalidIBAN() {
        IbanHandler ibanHandler = new IbanHandler(TestData.getInvalidPaymentInitiationRequest());
        assertFalse(ibanHandler.verify());
    }
}
