package com.progressoft.jip.payment.purpose.service;

import com.progressoft.jip.payment.purpose.PaymentPurposeDTO;
import com.progressoft.jip.payment.purpose.dao.impl.PaymentPurposeDAO;

public class PaymentPurposePersistenceServiceImpl implements PaymentPurposePersistenceService {

	private PaymentPurposeDAO paymentPurposeDAO;

	public PaymentPurposePersistenceServiceImpl(PaymentPurposeDAO paymentPurposeDAO) {
		this.paymentPurposeDAO = paymentPurposeDAO;
	}

	@Override
	public PaymentPurposeDTO save(PaymentPurposeDTO paymentPurposeDTO) {
		return this.paymentPurposeDAO.save(paymentPurposeDTO);
	}

	@Override
	public PaymentPurposeDTO getPaymentPurpose(String shortCode) {
		return this.paymentPurposeDAO.get(shortCode);
	}

	@Override
	public Iterable<PaymentPurposeDTO> getAll() {
		return this.paymentPurposeDAO.getAll();
	}

}
