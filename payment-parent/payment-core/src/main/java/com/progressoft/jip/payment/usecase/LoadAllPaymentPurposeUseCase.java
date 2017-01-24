package com.progressoft.jip.payment.usecase;

import com.progressoft.jip.payment.purpose.PaymentPurposeDTO;
import com.progressoft.jip.payment.purpose.service.PaymentPurposePersistenceService;

public class LoadAllPaymentPurposeUseCase {

	private PaymentPurposePersistenceService paymentPurposePersistenceService;

	public LoadAllPaymentPurposeUseCase(PaymentPurposePersistenceService paymentPurposePersistenceService) {
		this.paymentPurposePersistenceService = paymentPurposePersistenceService;
	}

	public Iterable<PaymentPurposeDTO> loadPaymentPurpose() {
		return paymentPurposePersistenceService.getAll();
	}

}
