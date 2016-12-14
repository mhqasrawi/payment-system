package com.progressoft.jip.payment;

public interface PaymentBuilder {

	PaymentBuilder setPaymentProcessor(PaymentProcessor paymentProcessor);
	
	Payment getNewPayment(PaymentInfo paymentInfo);
}
