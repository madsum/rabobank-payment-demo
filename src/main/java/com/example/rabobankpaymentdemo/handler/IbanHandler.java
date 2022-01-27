package com.example.rabobankpaymentdemo.handler;

import com.example.rabobankpaymentdemo.model.ErrorReasonCode;
import com.example.rabobankpaymentdemo.model.PaymentInitiationRequest;
import lombok.extern.slf4j.Slf4j;
import org.iban4j.IbanUtil;

import static com.example.rabobankpaymentdemo.model.ErrorReasonCode.INVALID_REQUEST;
import static com.example.rabobankpaymentdemo.model.ErrorReasonCode.VALID_IBAN;

@Slf4j
public class IbanHandler extends TppRequestHandler {

    public IbanHandler(PaymentInitiationRequest paymentInitiationRequestBody) {
        super(null, null, paymentInitiationRequestBody, null);
    }

    private int sumOfValidIbanAccountDigit(String iban){
        int sum = 0;
        if(validateIban(iban) == VALID_IBAN){
            char[] chars =   iban.toCharArray();
            for(int i=0;  i < chars.length; i++ ){
                if(Character.isDigit(chars[i])){
                    sum +=  Character.getNumericValue(chars[i]);
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
        if(  amount > 0 &&  sumOfValidIbanAccountDigit(paymentInitiationRequestBody.getDebtorIBAN()) %
                paymentInitiationRequestBody.getDebtorIBAN().length() == 0 ){
            return true;
        }
        return false;
    }
}
