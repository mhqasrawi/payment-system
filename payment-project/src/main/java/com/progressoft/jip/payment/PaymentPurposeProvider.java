package com.progressoft.jip.payment;

public interface PaymentPurposeProvider {

    PaymentPurpose get(String shortCode);

}
