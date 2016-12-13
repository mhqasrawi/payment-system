package com.progressoft.jip.payment;

public interface PaymentPurposeDAO {

	PaymentPurposeDTO get(String shortCode);

    void save(PaymentPurposeDTO paymentPurpose);
}
