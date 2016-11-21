package com.progressoft.jip.payment.purpose.service;

import com.progressoft.jip.payment.purpose.PaymentPurposeDTO;

public interface PaymentPurposePersistenceService {

	PaymentPurposeDTO save(PaymentPurposeDTO paymentPurposeDTO);

	PaymentPurposeDTO getPaymentPurpose(String shortCode);

}
