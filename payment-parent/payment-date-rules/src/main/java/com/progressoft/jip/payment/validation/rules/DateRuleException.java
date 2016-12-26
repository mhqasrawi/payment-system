package com.progressoft.jip.payment.validation.rules;

public class DateRuleException extends RuntimeException {

	public DateRuleException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	public DateRuleException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public DateRuleException(String arg0) {
		super(arg0);
	}

	public DateRuleException(Throwable arg0) {
		super(arg0);
	}

}
