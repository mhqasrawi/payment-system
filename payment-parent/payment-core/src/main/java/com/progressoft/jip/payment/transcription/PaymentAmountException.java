package com.progressoft.jip.payment.transcription;

public class PaymentAmountException extends RuntimeException {

	private static final long serialVersionUID = -8171505663425743668L;

	public PaymentAmountException() {
		super();
	}

	public PaymentAmountException(String message, Throwable cause, boolean enableSuppression,
                                  boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public PaymentAmountException(String message, Throwable cause) {
		super(message, cause);
	}

	public PaymentAmountException(String message) {
		super(message);
	}

	public PaymentAmountException(Throwable cause) {
		super(cause);
	}

}
