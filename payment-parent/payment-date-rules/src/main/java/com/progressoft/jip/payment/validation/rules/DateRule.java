package com.progressoft.jip.payment.validation.rules;

import com.progressoft.jip.payment.PaymentInfo;

public interface DateRule {

    boolean validatePaymentDate(PaymentInfo paymentInfo);

    String getId();

    String getDescription();
}
