package com.example.rabobankpaymentdemo.handler;

import com.example.rabobankpaymentdemo.TestData;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

class IbanHandlerTest {

    @Test
    void verify() {
        IbanHandler ibanHandler = new IbanHandler(TestData.getPaymentInitiationRequest());
        assertFalse(ibanHandler.verify());
    }
}
