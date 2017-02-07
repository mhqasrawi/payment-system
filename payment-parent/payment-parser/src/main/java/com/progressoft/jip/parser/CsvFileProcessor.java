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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.progressoft.jip.payment.PaymentDAO;
import com.progressoft.jip.payment.PaymentDTO.PaymentState;
import com.progressoft.jip.payment.PaymentDTO.PaymentStatus;
import com.progressoft.jip.payment.PaymentInfo;
import com.progressoft.jip.payment.account.AccountDTO;
import com.progressoft.jip.payment.account.AccountDTOImpl;
import com.progressoft.jip.payment.account.service.AccountPersistenceService;
import com.progressoft.jip.payment.iban.IBANDTO;
import com.progressoft.jip.payment.iban.IBANDTOImpl;
import com.progressoft.jip.payment.iban.IBANValidationException;
import com.progressoft.jip.payment.impl.PaymentDTOImpl;
import com.progressoft.jip.payment.impl.PaymentValidation;
import com.progressoft.jip.payment.purpose.PaymentPurposeDTO;
import com.progressoft.jip.payment.purpose.PaymentPurposeDTOImpl;
import com.progressoft.jip.payment.validation.rules.DateValidationRules.DateValidationRulesException;

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
	private PaymentDTOImpl paymentDTO;
	private PaymentDAO paymentDao;

	private static final Logger LOGGER = LoggerFactory.getLogger(CsvFileProcessor.class);

	public void setPaymentDao(PaymentDAO paymentDao) {
		this.paymentDao = paymentDao;
	}

	public void setPaymentDTO(PaymentDTOImpl paymentDTO) {
		this.paymentDTO = paymentDTO;
	}

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
				try {
					String paymentRecord[] = line.split(COMMA_DELIMITER);
					accountIbanValidation.validate(
							new PaymentInfoImpl(paymentRecord[ORDERING_ACCOUNT_IBAN], paymentRecord[BENEFICIARY_IBAN]));

					AccountDTO account = accountPersistence.getAccount(paymentRecord[ORDERING_ACCOUNT_NUMBER]);
					if (Objects.nonNull(account)) {
						dateValidationRules.validate(new PaymentInfoImpl(account, paymentRecord[SETTLEMENT_DATE]));
						initializeNewPayment(paymentRecord, account);
						paymentDao.save(paymentDTO);
					}
				} catch (IBANValidationException | DateValidationRulesException e) {
					LOGGER.error(e.getMessage());
				}
			}

		} catch (IOException e) {
			LOGGER.error(e.getMessage());
			throw new IllegalArgumentException(e);
		}
	}

	private void initializeNewPayment(String[] paymentRecord, AccountDTO account) {
		paymentDTO.setOrderingAccount(account);
		IBANDTOImpl ibanDto = new IBANDTOImpl();
		ibanDto.setIbanValue(paymentRecord[BENEFICIARY_IBAN]);
		paymentDTO.setBeneficiaryIBAN(ibanDto);
		paymentDTO.setBeneficiaryName(paymentRecord[BENEFICIARY_NAME]);
		paymentDTO.setPaymentAmount(new BigDecimal(paymentRecord[PAYMENT_AMOUNT]));
		PaymentPurposeDTOImpl paymentPurpose = new PaymentPurposeDTOImpl();
		paymentPurpose.setShortCode(paymentRecord[PAYMENT_PURPOSE]);
		paymentDTO.setPaymentPurpose(paymentPurpose);
		paymentDTO.setTransferCurrency(Currency.getInstance(paymentRecord[TRANSFER_CURRENCY]));
		paymentDTO.setSettlementDate(convertToLocalDateTime(paymentRecord[SETTLEMENT_DATE]));
		paymentDTO.setCreationDate(LocalDateTime.now());
		paymentDTO.setState(PaymentState.CREATED);
		paymentDTO.setStatusReason("");
		paymentDTO.setStatus(PaymentStatus.EMPTY);
	}

	private class PaymentInfoImpl implements PaymentInfo {
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
			LocalDateTime date = convertToLocalDateTime(settlmentDate);
			return date;
		}

		@Override
		public LocalDateTime getCreationDate() {
			return null;
		}

		@Override
		public PaymentState getState() {
			return null;
		}
	}

	private LocalDateTime convertToLocalDateTime(String settlmentDate) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
		LocalDateTime date = LocalDateTime.parse(settlmentDate, formatter);
		return date;
	}

}
