package com.progressoft.jip.payment.impl;

import javax.inject.Inject;

import com.progressoft.jip.payment.PaymentInfo;
import com.progressoft.jip.payment.iban.IBANValidator;

public class AccountIbanValidation implements PaymentValidation{
	
	@Inject
	private IBANValidator ibanValidator;

	@Override
	public void validate(PaymentInfo info) {
		ibanValidator.validate(info.getBeneficiaryIBAN());
		ibanValidator.validate(info.getOrderingAccount().getIban());
	}

}
