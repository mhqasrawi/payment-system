package com.progressoft.jip.payment.validation.rules.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;

import org.junit.Test;

import com.progressoft.jip.payment.PaymentInfo;
import com.progressoft.jip.payment.account.AccountDTO;
import com.progressoft.jip.payment.iban.IBANDTO;
import com.progressoft.jip.payment.purpose.PaymentPurposeDTO;
import com.progressoft.jip.payment.validation.rules.DateRule;
import com.progressoft.jip.payment.validation.rules.SameMonthDateRule;

public class SameMonthDateRuleTest {
	private DateRule sameMonthDateRule = new SameMonthDateRule();
	private PaymentInfoMock paymentInfo = new PaymentInfoMock();

	@Test
	public void sameMonthDateRuleIdAndDiscriptionShouldBeCorrect() {
		assertEquals("same-month", sameMonthDateRule.getId());
		assertEquals("payment should be within the same month", sameMonthDateRule.getDescription());

	}

	@Test
	public void givenPaymentDateWithSameMonthThenValidationShouldBeTrue() {
		paymentInfo.setPaymentDate(LocalDateTime.now());
		assertTrue(sameMonthDateRule.validatePaymentDate(paymentInfo));
	}

	@Test
	public void givenPaymentDateWithNextTwoMonthsThenValidationShouldBeFalse() {
		paymentInfo.setPaymentDate(LocalDateTime.now().plusMonths(2));
		assertFalse(sameMonthDateRule.validatePaymentDate(paymentInfo));
	}

	@Test
	public void givenPaymentDateInThePastThenValidationShouldBeFalse() {
		paymentInfo.setPaymentDate(LocalDateTime.now().minusMonths(2));
		assertFalse(sameMonthDateRule.validatePaymentDate(paymentInfo));
	}
	
	private class PaymentInfoMock implements PaymentInfo {
		private LocalDateTime paymentDate;

		@Override
		public AccountDTO getOrderingAccount() {
			return null;
		}

		@Override
		public IBANDTO getBeneficiaryIBAN() {
			return null;
		}

		@Override
		public String getBeneficiaryName() {
			return null;
		}

		@Override
		public BigDecimal getPaymentAmount() {
			return null;
		}

		@Override
		public Currency getTransferCurrency() {
			return null;
		}

		@Override
		public LocalDateTime getPaymentDate() {
			return paymentDate;
		}

		@Override
		public PaymentPurposeDTO getPaymentPurpose() {
			return null;
		}

		public void setPaymentDate(LocalDateTime paymentDate) {
			this.paymentDate = paymentDate;
		}
	}
}
