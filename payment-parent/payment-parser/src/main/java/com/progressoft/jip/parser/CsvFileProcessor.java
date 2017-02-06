package com.progressoft.jip.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Currency;
import java.util.Objects;

import com.progressoft.jip.payment.PaymentDTO.PaymentState;
import com.progressoft.jip.payment.PaymentDTO.PaymentStatus;
import com.progressoft.jip.payment.PaymentInfo;
import com.progressoft.jip.payment.account.AccountDTO;
import com.progressoft.jip.payment.account.AccountDTOImpl;
import com.progressoft.jip.payment.account.service.AccountPersistenceService;
import com.progressoft.jip.payment.iban.IBANDTO;
import com.progressoft.jip.payment.iban.IBANDTOImpl;
import com.progressoft.jip.payment.impl.PaymentValidation;
import com.progressoft.jip.payment.purpose.PaymentPurposeDTO;

public class CsvFileProcessor implements FileProcessor {
	private static final int ORDERING_ACCOUNT_IBAN = 0;
	private static final int ORDERING_ACCOUNT_NUMBER = 1;
	private static final int BENEFICIARY_IBAN = 2;
	private static final int BENEFICIARY_NAME = 3;
	private static final int PAYMENT_AMOUNT = 4;
	private static final int TRANSFER_CURRENCY = 5;
	private static final int PAYMENT_PURPOSE = 6;
	private static final int SETTLEMENT_DATE = 7;
	private static final String COMMA_DELIMITER = ",";

	private PaymentValidation accountIbanValidation;
	private PaymentValidation dateValidationRules;
	private AccountPersistenceService accountPersistence;

	public void setAccountIbanValidation(PaymentValidation accountIbanValidation) {
		this.accountIbanValidation = accountIbanValidation;
	}

	public void setDateValidationRules(PaymentValidation dateValidationRules) {
		this.dateValidationRules = dateValidationRules;
	}

	public void setAccountPersistence(AccountPersistenceService accountPersistence) {
		this.accountPersistence = accountPersistence;
	}

	@Override
	public void process(Path path) {
		try (BufferedReader reader = Files.newBufferedReader(path)) {
			String line = null;
			while ((line = reader.readLine()) != null) {
				String paymentRecord[] = line.split(COMMA_DELIMITER);
				accountIbanValidation.validate(
						new PaymentInfoImpl(paymentRecord[ORDERING_ACCOUNT_IBAN], paymentRecord[BENEFICIARY_IBAN]));

				AccountDTO account = accountPersistence.getAccount(paymentRecord[ORDERING_ACCOUNT_NUMBER]);
				if (Objects.nonNull(account)) {
					dateValidationRules.validate(new PaymentInfoImpl(account, paymentRecord[SETTLEMENT_DATE]));

				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static class PaymentInfoImpl implements PaymentInfo {
		private String orderingIban;
		private String beneficiaryIban;
		private AccountDTO orderingAccount;
		private String settlmentDate;

		public PaymentInfoImpl(String orderingIban, String beneficiaryIban) {
			this.orderingIban = orderingIban;
			this.beneficiaryIban = beneficiaryIban;
		}

		public PaymentInfoImpl(AccountDTO orderingAccount, String settlmentDate) {
			this.orderingAccount = orderingAccount;
			this.settlmentDate = settlmentDate;
		}

		@Override
		public AccountDTO getOrderingAccount() {
			if (Objects.isNull(orderingAccount)) {
				IBANDTOImpl ibanDto = new IBANDTOImpl();
				ibanDto.setIbanValue(orderingIban);
				AccountDTOImpl account = new AccountDTOImpl();
				account.setIbandto(ibanDto);
				return account;
			}
			return orderingAccount;
		}

		@Override
		public IBANDTO getBeneficiaryIBAN() {
			IBANDTOImpl iban = new IBANDTOImpl();
			iban.setIbanValue(beneficiaryIban);
			return iban;
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
		public LocalDateTime getSettlementDate() {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
			LocalDateTime date = LocalDateTime.parse(settlmentDate, formatter);
			return date;
		}

		@Override
		public LocalDateTime getCreationDate() {
			return null;
		}

		@Override
		public PaymentState getState() {
			// TODO Auto-generated method stub
			return null;
		}
	}

}
