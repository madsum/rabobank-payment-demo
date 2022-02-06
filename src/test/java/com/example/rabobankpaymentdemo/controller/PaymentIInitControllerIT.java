package com.example.rabobankpaymentdemo.controller;

import com.example.rabobankpaymentdemo.TestData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
class PaymentIInitControllerIT {

    private MockMvc mockMvc;

    @Autowired
    private PaymentIInitController paymentIInitController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(paymentIInitController).build();
    }

    @Test
    void testInitiatePaymentForResponse201() throws Exception {

        mockMvc.perform(post(PaymentIInitController.PAYMENT_INITIATE_VERSION + PaymentIInitController.INITIATE_PAYMENT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("X-Request-Id", TestData.uuid)
                        .header("Signature-Certificate", TestData.CERTIFICATE)
                        .header("Signature", TestData.SIGNATURE)
                        .content(TestData.getValidPaymentInitiationRequestJson()))
                        .andExpect(status().isCreated());
    }

    @Test
    void testInitiatePaymentWithInvalidDataResponse400() throws Exception {

        mockMvc.perform(post(PaymentIInitController.PAYMENT_INITIATE_VERSION + PaymentIInitController.INITIATE_PAYMENT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("X-Request-Id", TestData.uuid)
                        .header("Signature-Certificate", TestData.CERTIFICATE)
                        .header("Signature", TestData.RABO_SIGNATURE)
                        .content(TestData.getValidPaymentInitiationRequestJson()))
                        .andExpect(status().isBadRequest());
    }

    @Test
    void testInitiatePaymentLimitExceedResponse422() throws Exception {
        TestData.getInvalidPaymentInitiationRequestJson();
        mockMvc.perform(post(PaymentIInitController.PAYMENT_INITIATE_VERSION + PaymentIInitController.INITIATE_PAYMENT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("X-Request-Id", TestData.uuid)
                        .header("Signature-Certificate", TestData.CERTIFICATE)
                        .header("Signature", TestData.SIGNATURE_422)
                        .content(TestData.getInvalidPaymentInitiationRequestJson()))
                        .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void testGetSignature() throws Exception {
       var result = mockMvc.perform(post(PaymentIInitController.PAYMENT_INITIATE_VERSION + PaymentIInitController.GET_SIGNATURE)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
                            .header("X-Request-Id", "29318e25-cebd-498c-888a-f77672f66500")
                            .content(TestData.getValidPaymentInitiationRequestJson()))
                            .andExpect(status().isOk())
                            .andReturn();

       assertNotNull(result.getResponse().getContentAsString());
    }
}
