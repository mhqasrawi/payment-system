package com.progressoft.jip;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;

import com.progressoft.jip.payment.PaymentDAO;
import com.progressoft.jip.payment.PaymentDTO;
import com.progressoft.jip.payment.PaymentInfo;
import com.progressoft.jip.payment.PaymentUseCaseProcessor;
import com.progressoft.jip.payment.account.AccountDTO;
import com.progressoft.jip.payment.iban.IBANDTO;
import com.progressoft.jip.payment.iban.dao.IBANDAO;
import com.progressoft.jip.payment.purpose.PaymentPurposeDTO;

public class NewPaymentUseCase implements PaymentUseCaseProcessor {

	private final PaymentDAO paymentDao;
	private final IBANDAO ibanDao;

	public NewPaymentUseCase(PaymentDAO paymentDao, IBANDAO ibanDao) {
		this.paymentDao = paymentDao;
		this.ibanDao = ibanDao;
	}

	@Override
	public void processPayment(PaymentInfo paymentInfo) {
		paymentDao.save(new PaymentDtoImpl(paymentInfo, ibanDao));
	}

	private static class PaymentDtoImpl implements PaymentDTO {
		private PaymentInfo paymentInfo;
		private IBANDAO ibanDao;

		public PaymentDtoImpl(PaymentInfo paymentInfo, IBANDAO ibanDao) {
			this.paymentInfo = paymentInfo;
			this.ibanDao = ibanDao;
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
			return ibanDao.get(paymentInfo.getBeneficiaryIBAN().getIBANValue());
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
