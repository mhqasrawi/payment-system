package com.progressoft.jip.payment;

import com.progressoft.jip.payment.purpose.PaymentPurposeDTO;

public interface PaymentPurposeDAO {

	PaymentPurposeDTO get(String shortCode);

	void save(PaymentPurposeDTO paymentPurpose);
}
