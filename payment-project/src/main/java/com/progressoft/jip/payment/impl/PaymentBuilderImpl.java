package com.progressoft.jip.payment.impl;

import javax.inject.Inject;

import org.springframework.context.ApplicationContext;

import com.progressoft.jip.payment.Payment;
import com.progressoft.jip.payment.PaymentBuilder;
import com.progressoft.jip.payment.PaymentInfo;
import com.progressoft.jip.payment.PaymentProcessor;

public class PaymentBuilderImpl implements PaymentBuilder {

	@Inject
	private PaymentProcessor paymentProcessor;
	@Inject
	private ApplicationContext appContext;

	@Override
	public Payment getNewPayment(PaymentInfo paymentInfo) {
		PaymentImpl payment = new PaymentImpl(paymentInfo, paymentProcessor);
		appContext.getAutowireCapableBeanFactory().autowireBean(payment);
		payment.validatePayment();
		return payment;
	}

	@Override
	public PaymentBuilder setPaymentProcessor(PaymentProcessor paymentProcessor) {
		this.paymentProcessor = paymentProcessor;
		return this;
	}

}
