package com.progressoft.jip.payment.validation.rules.test;

import com.progressoft.jip.payment.PaymentDTO.PaymentState;
import com.progressoft.jip.payment.PaymentInfo;
import com.progressoft.jip.payment.account.AccountDTO;
import com.progressoft.jip.payment.iban.IBANDTO;
import com.progressoft.jip.payment.purpose.PaymentPurposeDTO;
import com.progressoft.jip.payment.validation.rules.DateRule;
import com.progressoft.jip.payment.validation.rules.SameYearDateRule;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;

import static org.junit.Assert.*;

public class SameYearDateRuleTest {
	private DateRule sameYearDateRule = new SameYearDateRule();
	private PaymentInfoMock paymentInfo = new PaymentInfoMock();

	@Test
	public void givenNewSameYearDateRuleThenIdAndDescriptionShouldCorrect() {
		assertEquals("same-year", sameYearDateRule.getId());
		assertEquals("Payment should be within the same year", sameYearDateRule.getDescription());
	}

	@Test
	public void givenPaymentWithSameYearValidationShouldBeTrue() {
		paymentInfo.setPaymentDate(LocalDateTime.now());
		assertTrue(sameYearDateRule.validatePaymentDate(paymentInfo));
	}

	@Test
	public void givenPaymentInNextTwoYearsThenValidationShouldBeFalse() {
		paymentInfo.setPaymentDate(LocalDateTime.now().plusYears(2));
		assertFalse(sameYearDateRule.validatePaymentDate(paymentInfo));
	}

	@Test
	public void givenPaymentWithPreviousYearThenValidationShouldBeFalse() {
		paymentInfo.setPaymentDate(LocalDateTime.now().minusYears(1));
		assertFalse(sameYearDateRule.validatePaymentDate(paymentInfo));

	}

	private class PaymentInfoMock implements PaymentInfo {
		private LocalDateTime settlementDate;

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
		public LocalDateTime getSettlementDate() {
			return settlementDate;
		}

		public void setPaymentDate(LocalDateTime paymentDate) {
			this.settlementDate = paymentDate;
		}

		@Override
		public PaymentPurposeDTO getPaymentPurpose() {
			return null;
		}

		@Override
		public PaymentState getState() {
			return null;
		}

		@Override
		public LocalDateTime getCreationDate() {
			return null;
		}
	}
}
