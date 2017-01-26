package com.progressoft.jip.payment.impl;

import com.progressoft.jip.payment.Payment;
import com.progressoft.jip.payment.PaymentBuilder;
import com.progressoft.jip.payment.PaymentInfo;

public class PaymentBuilderImpl implements PaymentBuilder {

    @Override
    public Payment getNewPayment(PaymentInfo paymentInfo) {
        PaymentImpl payment = new PaymentImpl(paymentInfo);
        payment.validatePayment();
        return payment;
    }

}
