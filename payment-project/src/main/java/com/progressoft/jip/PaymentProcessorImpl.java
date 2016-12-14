package com.progressoft.jip;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;

import javax.inject.Inject;

import com.progressoft.jip.payment.PaymentDAO;
import com.progressoft.jip.payment.PaymentDTO;
import com.progressoft.jip.payment.PaymentInfo;
import com.progressoft.jip.payment.PaymentProcessor;
import com.progressoft.jip.payment.account.AccountDTO;
import com.progressoft.jip.payment.iban.IBANDTO;
import com.progressoft.jip.payment.purpose.PaymentPurposeDTO;

public class PaymentProcessorImpl implements PaymentProcessor {

	@Inject
	private PaymentDAO paymentDao;

	@Override
	public void processPayment(PaymentInfo paymentInfo) {
		paymentDao.save(new PaymentDtoImpl(paymentInfo));
	}

	private static class PaymentDtoImpl implements PaymentDTO {
		private PaymentInfo paymentInfo;

		public PaymentDtoImpl(PaymentInfo paymentInfo) {
			this.paymentInfo = paymentInfo;
		}

		@Override
		public int getId() {
			return 1;
		}

		@Override
		public AccountDTO getOrderingAccount() {
			return paymentInfo.getOrderingAccount();
		}

		@Override
		public IBANDTO getBeneficiaryIBAN() {
			return paymentInfo.getBeneficiaryIBAN();
		}

		@Override
		public String getBeneficiaryName() {
			return paymentInfo.getBeneficiaryName();
		}

		@Override
		public BigDecimal getPaymentAmount() {
			return paymentInfo.getPaymentAmount();
		}

		@Override
		public Currency getTransferCurrency() {
			return paymentInfo.getTransferCurrency();
		}

		@Override
		public LocalDateTime getPaymentDate() {
			return paymentInfo.getPaymentDate();
		}

		@Override
		public PaymentPurposeDTO getPaymentPurpose() {
			return paymentInfo.getPaymentPurpose();
		}

	}

}
