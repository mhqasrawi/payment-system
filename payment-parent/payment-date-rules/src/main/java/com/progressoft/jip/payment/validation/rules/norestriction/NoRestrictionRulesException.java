package com.progressoft.jip.payment.validation.rules.norestriction;

public class NoRestrictionRulesException extends RuntimeException{

	private static final long serialVersionUID = 3073205731586027657L;

	public NoRestrictionRulesException() {
		super();
	}

	public NoRestrictionRulesException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public NoRestrictionRulesException(String message, Throwable cause) {
		super(message, cause);
	}

	public NoRestrictionRulesException(String message) {
		super(message);
	}

	public NoRestrictionRulesException(Throwable cause) {
		super(cause);
	}

	
}
