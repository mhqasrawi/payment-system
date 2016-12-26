package com.progressoft.jip.payment.validation.rules.norestriction;

import java.util.Objects;

import com.progressoft.jip.payment.PaymentInfo;
import com.progressoft.jip.payment.validation.rules.DateRule;

public class NoRestrictionRules implements DateRule {

	private static final String NO_RESTRICATION_ON_DATE = "No Restrication On Date";
	private static final String NO_RESTRICTION_RULES_ID = "no-restriction-rules";

	@Override
	public boolean validatePaymentDate(PaymentInfo paymentInfo) {
		if (Objects.isNull(paymentInfo))
			throw new NoRestrictionRulesException("Payment Info Shouldn't Be Null");
		if(Objects.isNull(paymentInfo.getPaymentDate()))
				throw new NoRestrictionRulesException("Payment Date should't be Null");
		return true;
	}

	@Override
	public String getId() {
		return NO_RESTRICTION_RULES_ID;
	}

	@Override
	public String getDescription() {
		return NO_RESTRICATION_ON_DATE;
	}

}
