package com.progressoft.jip.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Currency;

import com.progressoft.jip.PaymentMenuContext;
import com.progressoft.jip.payment.PaymentInfo;
import com.progressoft.jip.payment.PaymentDTO.PaymentState;
import com.progressoft.jip.payment.account.AccountDTO;
import com.progressoft.jip.payment.iban.IBANDTO;
import com.progressoft.jip.payment.purpose.PaymentPurposeDTO;

public class CsvFileProcessor implements FileProcessor {
	private static final String ORDERING_ACCOUNT_NUMBER = "0";
	private static final String BENEFICIARY_IBAN = "1";
	private static final String BENEFICIARY_NAME = "2";
	private static final String PAYMENT_AMOUNT = "3";
	private static final String TRANSFER_CURRENCY = "4";
	private static final String PAYMENT_PURPOSE = "5";
	private static final String SETTLEMENT_DATE = "6";
	private static final String COMMA_DELIMITER = ",";

	@Override
	public void process(Path path) {
		try (BufferedReader reader = Files.newBufferedReader(path)) {
			String line = null;
			while ((line = reader.readLine()) != null) {
				String paymentRecord[] = line.split(COMMA_DELIMITER);

			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	 private static class PaymentInfoImpl implements PaymentInfo {

	        private AccountDTO orderingAccount;

	       

	        @Override
	        public AccountDTO getOrderingAccount() {
	            return orderingAccount;
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
			public LocalDateTime getSettlementDate() {
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
