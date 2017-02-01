package com.progressoft.jip.payment.validation.rules.test;

import com.progressoft.jip.payment.PaymentDTO.PaymentStatus;
import com.progressoft.jip.payment.PaymentInfo;
import com.progressoft.jip.payment.account.AccountDTO;
import com.progressoft.jip.payment.iban.IBANDTO;
import com.progressoft.jip.payment.purpose.PaymentPurposeDTO;
import com.progressoft.jip.payment.validation.rules.DateRule;
import com.progressoft.jip.payment.validation.rules.SameDayDateRule;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;

import static org.junit.Assert.*;

public class SameDayDateRuleTest {
	private DateRule sameDayDateRule = new SameDayDateRule();
	private PaymentInfoMock paymentInfo = new PaymentInfoMock();

	@Test
	public void givenNewSameDayDateRuleThenIdAndDescriptionShouldCorrect() {
		assertEquals("same-day", sameDayDateRule.getId());
		assertEquals("Payment should be within the same day", sameDayDateRule.getDescription());
	}

	@Test
	public void givenPaymentWithSameDayValidationShouldBeTrue() {
		paymentInfo.setPaymentDate(LocalDateTime.now());
		assertTrue(sameDayDateRule.validatePaymentDate(paymentInfo));
	}

	@Test
	public void givenPaymentInNextTwoDaysThenValidationShouldBeFalse() {
		paymentInfo.setPaymentDate(LocalDateTime.now().plusDays(2));
		assertFalse(sameDayDateRule.validatePaymentDate(paymentInfo));
	}

	@Test
	public void givenPaymentWithPreviousDayThenValidationShouldBeFalse() {
		paymentInfo.setPaymentDate(LocalDateTime.now().minusDays(1));
		assertFalse(sameDayDateRule.validatePaymentDate(paymentInfo));
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
		public LocalDateTime getSettlementDate() {
			return paymentDate;
		}

		public void setPaymentDate(LocalDateTime paymentDate) {
			this.paymentDate = paymentDate;
		}

		@Override
		public PaymentPurposeDTO getPaymentPurpose() {
			return null;
		}

		@Override
		public PaymentStatus getStatus() {
			return null;
		}

		@Override
		public LocalDateTime getCreationDate() {
			return null;
		}
	}

}
