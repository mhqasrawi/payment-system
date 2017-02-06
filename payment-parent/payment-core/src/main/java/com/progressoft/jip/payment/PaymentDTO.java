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

	PaymentState getState();

	LocalDateTime getCreationDate();

	PaymentStatus getStatus();

	String getStatusReason();

	public enum PaymentStatus {
		ACCEPTED(1), REJECTED(2);

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

	public enum PaymentState {
		CREATED(1), SUBMITTED(2);

		private int value;

		private PaymentState(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}

		public static PaymentState getState(int value) {
			for (PaymentState paymentState : PaymentState.values()) {
				if (paymentState.getValue() == value) {
					return paymentState;
				}
			}
			return null;
		}

	}
}
