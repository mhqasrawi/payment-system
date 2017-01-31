package com.progressoft.jip.payment.impl;

import com.progressoft.jip.payment.Payment;
import com.progressoft.jip.payment.PaymentInfo;
import com.progressoft.jip.payment.PaymentUseCaseProcessor;

import java.util.*;

public class PaymentImpl implements Payment {

    private final static List<PaymentValidation> paymentValidations;

    static {
        List<PaymentValidation> validators = new ArrayList<>();
        ServiceLoader<PaymentValidation> serviceLoader = ServiceLoader.load(PaymentValidation.class);
        Iterator<PaymentValidation> iterator = serviceLoader.iterator();
        iterator.forEachRemaining(validators::add);
        paymentValidations = Collections.unmodifiableList(validators);
    }


    private final PaymentInfo paymentInfo;

    PaymentImpl(PaymentInfo paymentInfo) {
        this.paymentInfo = paymentInfo;
    }

    void validatePayment() {
        paymentValidations.forEach((paymentValidation) -> paymentValidation.validate(paymentInfo));
    }

    public void doAction(PaymentUseCaseProcessor paymentProcessor) {
        paymentProcessor.processPayment(paymentInfo);
    }

}
