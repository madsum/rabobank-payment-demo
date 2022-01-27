package com.example.rabobankpaymentdemo.controller;

import com.example.rabobankpaymentdemo.TestData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class PaymentIInitControllerIT {

    private MockMvc mockMvc;

    @InjectMocks
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
                        .header("X-Request-Id", "29318e25-cebd-498c-888a-f77672f66450")
                        .header("Signature-Certificate", TestData.CERTIFICATE)
                        .header("Signature", TestData.SIGNATURE)
                        .content("{\n" +
                                "\"debtorIBAN\": \"NL02RABO7134384551\",\n" +
                                "\"creditorIBAN\": \"NL94ABNA1008270121\",\n" +
                                "\"amount\": \"11\",\n" +
                                "\"currency\": \"EUR\",\n" +
                                "\"endToEndId\": \"endToEndId\"\n" +
                                "\n" +
                                "}"))
                        .andExpect(status().isCreated());
    }

    @Test
    void testInitiatePaymentWithInvalidDataResponse400() throws Exception {
       mockMvc.perform(post(PaymentIInitController.PAYMENT_INITIATE_VERSION + PaymentIInitController.INITIATE_PAYMENT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("X-Request-Id", "29318e25-cebd-498c-888a-f77672f66449")
                        .header("Signature-Certificate", TestData.CERTIFICATE)
                        .header("Signature", TestData.RABO_SIGNATURE)
                        .content("{\"debtorIBAN\":\"NL02RABO7134384551\",\"creditorIBAN\":\"NL94ABNA1008270121\",\"amount\":\"1.00\"}"))
                        .andExpect(status().isBadRequest());
    }

    @Test
    void testInitiatePaymentLimitExceedResponse422() throws Exception {
        mockMvc.perform(post(PaymentIInitController.PAYMENT_INITIATE_VERSION + PaymentIInitController.INITIATE_PAYMENT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("X-Request-Id", "29318e25-cebd-498c-888a-f77672f66500")
                        .header("Signature-Certificate", TestData.CERTIFICATE)
                        .header("Signature", TestData.SIGNATURE_201)
                        .content("{\n" +
                                "\"debtorIBAN\": \"NL02RABO7134384113\",\n" +
                                "\"creditorIBAN\": \"NL94ABNA1008270121\",\n" +
                                "\"amount\": \"500\",\n" +
                                "\"currency\": \"EUR\",\n" +
                                "\"endToEndId\": \"endToEndId\"\n" +
                                "\n" +
                                "}"))
                .andExpect(status().isUnprocessableEntity());
    }
}
