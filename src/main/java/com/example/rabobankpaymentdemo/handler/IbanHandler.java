package com.example.rabobankpaymentdemo.handler;

import com.example.rabobankpaymentdemo.model.ErrorReasonCode;
import com.example.rabobankpaymentdemo.model.PaymentInitiationRequest;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.iban4j.IbanUtil;
import org.springframework.stereotype.Service;

import static com.example.rabobankpaymentdemo.model.ErrorReasonCode.INVALID_REQUEST;
import static com.example.rabobankpaymentdemo.model.ErrorReasonCode.VALID_IBAN;

@Slf4j
@Service
@NoArgsConstructor
public class IbanHandler extends TppRequestHandler {

    public IbanHandler(PaymentInitiationRequest paymentInitiationRequestBody) {
        super(null, null,  null, paymentInitiationRequestBody);
    }

    private int sumOfValidIbanAccountDigit(String iban){
        int sum = 0;
        if(validateIban(iban) == VALID_IBAN){
            char[] chars =   iban.toCharArray();
            for (char aChar : chars) {
                if (Character.isDigit(aChar)) {
                    sum += Character.getNumericValue(aChar);
                }
            }
        }
        return sum;
    }

    private ErrorReasonCode validateIban(String iban){
        try {
            IbanUtil.validate(iban);
            log.info("valid IBAN: "+iban);
            return VALID_IBAN;
        } catch ( Exception e) {
            log.warn("invalid IBAN: "+iban);
            return INVALID_REQUEST;
        }
    }

    public boolean verify()  {
        double amount = Double.parseDouble(paymentInitiationRequestBody.getAmount());
        return !(amount > 0) || sumOfValidIbanAccountDigit(paymentInitiationRequestBody.getDebtorIBAN()) %
                paymentInitiationRequestBody.getDebtorIBAN().length() != 0;
    }
}
