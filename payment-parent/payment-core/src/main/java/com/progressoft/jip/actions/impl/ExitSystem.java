package com.progressoft.jip.actions.impl;

import com.progressoft.jip.PaymentMenuContext;
import com.progressoft.jip.actions.PaymentAction;

/**
 * @author Ahmad.Jardat
 *
 */
public class ExitSystem implements PaymentAction{

	@Override
	public PaymentMenuContext doAction(PaymentMenuContext menuContext) {
		System.exit(0);
		return null;
	}

}
