package com.example.rabobankpaymentdemo.handler;

import com.example.rabobankpaymentdemo.TestData;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class IbanHandlerTest {

    @Test
    void verifyInvalid() {
        IbanHandler ibanHandler = new IbanHandler(TestData.getPaymentInitiationRequest());
        assertFalse(ibanHandler.verify());
    }

    @Test
    void verifyValid() {
        IbanHandler ibanHandler = new IbanHandler(TestData.getValidPaymentInitiationRequest());
        assertTrue(ibanHandler.verify());
    }

}
