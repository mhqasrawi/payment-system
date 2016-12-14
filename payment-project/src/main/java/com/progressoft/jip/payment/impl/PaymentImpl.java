package com.progressoft.jip.payment.impl;

import javax.inject.Inject;

import com.progressoft.jip.payment.Payment;
import com.progressoft.jip.payment.PaymentInfo;
import com.progressoft.jip.payment.PaymentProcessor;
import com.progressoft.jip.payment.iban.IBANValidator;

public class PaymentImpl implements Payment {

	private final PaymentInfo paymentInfo;
	private final PaymentProcessor paymentProcessor;
	
	@Inject
	private IBANValidator ibanValidator;

	PaymentImpl(PaymentInfo paymentInfo, PaymentProcessor paymentProcessor) {
		this.paymentInfo = paymentInfo;
		this.paymentProcessor = paymentProcessor;
	}
	
	public void validatePayment(){
		validateOrdaringAccount();
		validateBeneficaryAccount();
	}


	private void validateOrdaringAccount() {
		ibanValidator.validate(paymentInfo.getOrderingAccount().getIban());
	}

	private void validateBeneficaryAccount() {
		ibanValidator.validate(paymentInfo.getBeneficiaryIBAN());
	}

	@Override
	public void commitPayment() {
		paymentProcessor.processPayment(paymentInfo);
	}

}
