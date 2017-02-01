package com.progressoft.jip.payment;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;

import com.progressoft.jip.payment.account.AccountDTO;
import com.progressoft.jip.payment.iban.DTO;
import com.progressoft.jip.payment.iban.IBANDTO;
import com.progressoft.jip.payment.purpose.PaymentPurposeDTO;

public interface PaymentDTO extends DTO {
	AccountDTO getOrderingAccount();

	IBANDTO getBeneficiaryIBAN();

	String getBeneficiaryName();

	BigDecimal getPaymentAmount();

	Currency getTransferCurrency();

	LocalDateTime getSettlementDate();

	PaymentPurposeDTO getPaymentPurpose();

	PaymentStatus getStatus();

	LocalDateTime getCreationDate();

	public enum PaymentStatus {
		CREATED(1), APPROVED(2), REJECTED(3);

		private int value;

		private PaymentStatus(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}

		public static PaymentStatus getStatus(int value) {
			for (PaymentStatus paymentStatus : PaymentStatus.values()) {
				if (paymentStatus.getValue() == value) {
					return paymentStatus;
				}
			}
			return null;
		}
	}
}
