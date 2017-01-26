package com.progressoft.jip.payment.impl;

import com.progressoft.jip.payment.PaymentInfo;
import com.progressoft.jip.payment.iban.IBANValidator;

import javax.inject.Inject;

public class AccountIbanValidation implements PaymentValidation {

    @Inject
    private IBANValidator ibanValidator;

    @Override
    public void validate(PaymentInfo info) {
        ibanValidator.validate(info.getBeneficiaryIBAN());
        ibanValidator.validate(info.getOrderingAccount().getIban());
    }

}
