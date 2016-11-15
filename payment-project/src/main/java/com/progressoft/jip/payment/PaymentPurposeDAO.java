package com.progressoft.jip.payment;

public interface PaymentPurposeDAO {

    PaymentPurpose get(String shortCode);

    void save(PaymentPurpose paymentPurpose);
}
