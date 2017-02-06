package com.progressoft.jip.payment.validation.rules.test;

import com.progressoft.jip.payment.PaymentDTO.PaymentState;
import com.progressoft.jip.payment.PaymentInfo;
import com.progressoft.jip.payment.account.AccountDTO;
import com.progressoft.jip.payment.iban.IBANDTO;
import com.progressoft.jip.payment.purpose.PaymentPurposeDTO;
import com.progressoft.jip.payment.validation.rules.DateRule;
import com.progressoft.jip.payment.validation.rules.SameMonthDateRule;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;

import static org.junit.Assert.*;

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

        public void setPaymentDate(LocalDateTime paymentDate) {
            this.settlementDate = paymentDate;
        }

        @Override
        public PaymentPurposeDTO getPaymentPurpose() {
            return null;
        }

		@Override
		public LocalDateTime getSettlementDate() {
			return settlementDate;
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
