package com.progressoft.jip.test;

import java.math.BigDecimal;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Currency;
import java.util.LinkedList;
import java.util.List;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import com.progressoft.jip.payment.PaymentDTO;
//import com.progressoft.jip.payment.PaymentPurpose;
import com.progressoft.jip.payment.account.AccountDTO;
import com.progressoft.jip.payment.account.AccountDTO.AccountStatus;
import com.progressoft.jip.payment.account.AccountDTOImpl;
import com.progressoft.jip.payment.customer.CustomerDTOImpl;
import com.progressoft.jip.payment.iban.IBANDTO;
import com.progressoft.jip.payment.iban.IBANDTOImpl;
import com.progressoft.jip.payment.purpose.PaymentPurposeDTO;
import com.progressoft.jip.payment.report.core.ReportGeneratorException;
import com.progressoft.jip.payment.report.core.ReportManagerException;
import com.progressoft.jip.payment.report.core.ReportSettings;
import com.progressoft.jip.payment.report.impl.ReportManagerImpl;

public class ReportTestCases {
	private ReportManagerImpl reportManager = new ReportManagerImpl();
	private List<PaymentDTO> payments = new LinkedList<>();
	private AccountDTOImpl account = new AccountDTOImpl();
	private CustomerDTOImpl customer = new CustomerDTOImpl();
	private List<AccountDTO> accounts = new LinkedList<>();
	private IBANDTOImpl iban = new IBANDTOImpl();
	private PaymentPurposeDTO purpose = new PurposeDTOImpl("shortCode", "purpose description");
	private PaymentDTOImpl payment = new PaymentDTOImpl();
	private String beneficiaryName;
	private BigDecimal paymentAmount;
	private Currency transferCurrency = Currency.getAvailableCurrencies().iterator().next();
	private LocalDateTime date;

	private class PaymentDTOImpl implements PaymentDTO {
		private AccountDTO account;
		private IBANDTO iban;
		private String beneficiaryName;
		private BigDecimal amount;
		private Currency currency;
		private LocalDateTime date;
		private PaymentPurposeDTO paymentPurpose;

		public PaymentDTOImpl() {
		}

		public void setAccount(AccountDTO account) {
			this.account = account;
		}

		public void setIban(IBANDTO iban) {
			this.iban = iban;
		}

		public void setBeneficiaryName(String beneficiaryName) {
			this.beneficiaryName = beneficiaryName;
		}

		public void setAmount(BigDecimal amount) {
			this.amount = amount;
		}

		public void setCurrency(Currency currency) {
			this.currency = currency;
		}

		public void setDate(LocalDateTime date) {
			this.date = date;
		}

		public void setPaymentPurpose(PaymentPurposeDTO paymentPurpose) {
			this.paymentPurpose = paymentPurpose;
		}

		@Override
		public AccountDTO getOrderingAccount() {
			return account;
		}

		@Override
		public IBANDTO getBeneficiaryIBAN() {
			return iban;
		}

		@Override
		public BigDecimal getPaymentAmount() {
			return amount;
		}

		@Override
		public Currency getTransferCurrency() {
			return currency;
		}

		@Override
		public LocalDateTime getPaymentDate() {
			return date;
		}

		@Override
		public String getBeneficiaryName() {
			return beneficiaryName;
		}

		@Override
		public PaymentPurposeDTO getPaymentPurpose() {
			return purpose;
		}

		@Override
		public int getId() {
			return 0;
		}
	}

	private class PurposeDTOImpl implements PaymentPurposeDTO {
		String shortCode;
		String description;

		public PurposeDTOImpl(String shortCode, String description) {
			this.shortCode = shortCode;
			this.description = description;
		}

		public String getShortCode() {
			return shortCode;
		}

		public String getDescription() {
			return description;
		}

		@Override
		public int getId() {
			return 0;
		}
	}

	@Before
	public void setup() {
		iban.setIbanValue("123456789");
		date = LocalDateTime.now();
		customer.setName("yahia");
		customer.setId(1);
		customer.setAccounts(accounts);
		beneficiaryName = "anas";
		paymentAmount = BigDecimal.valueOf(new Long(1000));

		account.setAccountName("accountName");
		account.setAccountNumber("123");
		account.setAccountStatus(AccountStatus.ACTIVE);
		account.setCurrency(transferCurrency);
		account.setCustomerDTO(customer);
		account.setIbandto(iban);

		accounts.add(account);

		payment.setAccount(account);
		payment.setAmount(paymentAmount);
		payment.setBeneficiaryName(beneficiaryName);
		payment.setCurrency(transferCurrency);
		payment.setDate(date);
		payment.setIban(iban);
		payment.setPaymentPurpose(purpose);

		payments.add(payment);
		payments.add(payment);
	}

	@Test(expected = NullPointerException.class)
	public void callingGenerateReportMethod_WithNullSettings_ShouldThrowNullPointerException() throws ReportManagerException {
			reportManager.generateReport(null);
	}
	
	@Test(expected = ReportManagerException.class)
	public void callingGenerateReportMethod_WithNullSettingsParams_ShouldThrowNullPointerException() throws ReportManagerException {
		ReportSettings settings = new ReportSettings();
		settings.setFileExtention(null);
		settings.setFileName(null);
		settings.setPath(null);
		settings.setPayments(null);
			reportManager.generateReport(settings);
	}

	@SuppressWarnings("unchecked")
	@Test(expected = ReportManagerException.class)
	public void callingGenerateReportMethod_WithEmptySettingsParams_ShouldThrowReportManagerException() throws ReportManagerException {
		ReportSettings settings = new ReportSettings();
		settings.setFileExtention("");
		settings.setFileName("");
		settings.setPath(Paths.get("C:\\"));
		settings.setPayments((Iterable<PaymentDTO>)Collections.EMPTY_LIST);
		reportManager.generateReport(settings);
	}

	@Test(expected = ReportManagerException.class)
	public void callingGenerateReportMethod_WithUnsupportedExtension_ShouldThrowReportException() throws ReportManagerException {
		ReportSettings settings = new ReportSettings();
		settings.setFileExtention("xyz");
		settings.setFileName("name");
		settings.setPath(Paths.get("C:/"));
		settings.setPayments(payments);
		reportManager.generateReport(settings);
	}
	
	@Test
	public void callingGenerateReportMethod_WithValidParams_ShouldGenerateReport() throws ReportManagerException {
		ReportSettings settings = new ReportSettings();
		settings.setFileName("report");
		settings.setPath(Paths.get("C:/Users/u620/Desktop/Success"));
		settings.setFileExtention("csv");
		settings.setPayments(payments);
		reportManager.generateReport(settings);
		settings.setFileExtention("xml");
		reportManager.generateReport(settings);
	}
}