package com.progressoft.jip.payment.validation.rules;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import com.progressoft.jip.payment.PaymentInfo;

public class SameMonthDateRule implements DateRule {

	private static final int DAYS_OF_MONTH = 30;

	@Override
	public boolean validatePaymentDate(PaymentInfo paymentInfo) {
		if (isPastPaymentDate(paymentInfo))
			return false;
		if (isPaymentDateWithinOneMonth(paymentInfo))
			return true;
		return false;
	}

	private boolean isPastPaymentDate(PaymentInfo paymentInfo) {
		return Duration.between(
				LocalDateTime.of(LocalDate.now(), LocalTime.of(LocalTime.now().getHour(), LocalTime.now().getMinute())),
				paymentInfo.getPaymentDate()).isNegative();
	}

	private boolean isPaymentDateWithinOneMonth(PaymentInfo paymentInfo) {
		return Duration.between(LocalDateTime.now(), paymentInfo.getPaymentDate())
				.compareTo(Duration.ofDays(DAYS_OF_MONTH)) <= 0;
	}

	@Override
	public String getId() {
		return "same-month";
	}

	@Override
	public String getDescription() {
		return "payment should be within the same month";
	}

}
