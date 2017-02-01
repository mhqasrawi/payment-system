package com.progressoft.jip.payment.validation.rules.test;

import com.progressoft.jip.payment.PaymentDTO.PaymentStatus;
import com.progressoft.jip.payment.PaymentInfo;
import com.progressoft.jip.payment.account.AccountDTO;
import com.progressoft.jip.payment.customer.CustomerDTO;
import com.progressoft.jip.payment.iban.IBANDTO;
import com.progressoft.jip.payment.purpose.PaymentPurposeDTO;
import com.progressoft.jip.payment.validation.rules.CustomBoundDateRule;
import com.progressoft.jip.payment.validation.rules.DateRuleException;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CustomBoundDateRuleTest {

    private static final LocalDateTime now = LocalDateTime.now();
    AccountDTOMock account = new AccountDTOMock();
    private CustomBoundDateRule rule = new CustomBoundDateRule();
    private PaymentInfoMock paymentInfo = new PaymentInfoMock();

    private void setInfo(String days, LocalDateTime date) {
        account.setRuleInfo(days);
        this.paymentInfo.setAccount(account);
        this.paymentInfo.setDate(date);
    }

    @Test(expected = DateRuleException.class)
    public void givenNonNumericalInput_ThenThrowDateRuleException() {
        setInfo("a", now);
        rule.validatePaymentDate(paymentInfo);
    }

    @Test(expected = DateRuleException.class)
    public void givenNegativeInputDays_ThenThrowDateRuleException() {
        setInfo("-1", now);
        rule.validatePaymentDate(paymentInfo);
    }

    @Test(expected = DateRuleException.class)
    public void givenNullInputDays_ThenThrowDateRuleException() {
        setInfo(null, now);
        rule.validatePaymentDate(paymentInfo);
    }

    @Test(expected = DateRuleException.class)
    public void givenEmptyInputDays_ThenThrowDateRuleException() {
        setInfo("", now);
        rule.validatePaymentDate(paymentInfo);
    }

    @Test(expected = DateRuleException.class)
    public void givenPastDate_ThenThrowDateRuleException() {
        setInfo("", now);
        rule.validatePaymentDate(paymentInfo);
    }

    @Test()
    public void givenPaymentInTwoDaysWithRuleOfFourDays_ThenReturnTrue() {
        setInfo("4", now.plusDays(2));
        assertTrue(rule.validatePaymentDate(paymentInfo));
    }

    @Test()
    public void givenPaymentInFourDaysWithRuleOfTwoDays_ThenReturnFalse() {
        setInfo("2", now.plusDays(4));
        assertFalse(rule.validatePaymentDate(paymentInfo));
    }

    @Test()
    public void givenPaymentInZeroDaysWithRuleOfZeroDays_ThenReturnTrue() {
        setInfo("0", now);
        assertTrue(rule.validatePaymentDate(paymentInfo));
    }

    private class AccountDTOMock implements AccountDTO {
        String ruleInfo;

        @Override
        public String getPaymentRule() {
            return rule.getId();
        }

        @Override
        public String getPaymentRuleInfo() {
            return ruleInfo;
        }

        public void setRuleInfo(String info) {
            this.ruleInfo = info;
        }

        @Override
        public int getId() {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public String getAccountNumber() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public IBANDTO getIban() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public String getAccountName() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public CustomerDTO getCustomerDTO() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public Currency getCurreny() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public AccountStatus getAccountStatus() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public int getIbanId() {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public BigDecimal getBalance() {
            // TODO Auto-generated method stub
            return null;
        }

    }

    private class PaymentInfoMock implements PaymentInfo {
        private LocalDateTime settlementDate;
        private AccountDTO account;

        public void setDate(LocalDateTime date) {
            this.settlementDate = date;
        }

        public void setAccount(AccountDTO account) {
            this.account = account;
        }

        @Override
        public AccountDTO getOrderingAccount() {
            return account;
        }

        @Override
        public LocalDateTime getSettlementDate() {
            return settlementDate;
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