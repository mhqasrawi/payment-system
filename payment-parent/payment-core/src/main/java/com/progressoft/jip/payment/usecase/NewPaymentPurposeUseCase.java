package com.progressoft.jip.payment.usecase;

import com.progressoft.jip.PaymentMenuContext;
import com.progressoft.jip.payment.purpose.PaymentPurposeDTO;
import com.progressoft.jip.payment.purpose.dao.impl.PaymentPurposeDAO;

public class NewPaymentPurposeUseCase {

	private final PaymentPurposeDAO paymentPurposeDAO;

	public NewPaymentPurposeUseCase(PaymentPurposeDAO paymentPurposeDAO) {
		this.paymentPurposeDAO = paymentPurposeDAO;
	}

	public void process(PaymentPurposeDTO paymentPurpose) {
		paymentPurposeDAO.save(paymentPurpose);
	}

}
