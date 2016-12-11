package com.progressoft.jip.test.report;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.LinkedList;
import java.util.List;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import com.progressoft.jip.payment.PaymentDTO;
import com.progressoft.jip.payment.PaymentPurpose;
import com.progressoft.jip.payment.account.AccountDTO;
import com.progressoft.jip.payment.account.AccountDTO.AccountStatus;
import com.progressoft.jip.payment.account.AccountDTOImpl;
import com.progressoft.jip.payment.customer.CustomerDTOImpl;
import com.progressoft.jip.payment.iban.IBANDTO;
import com.progressoft.jip.payment.iban.IBANDTOImpl;
import com.progressoft.jip.payment.report.ReportGenerator;
import com.progressoft.jip.payment.report.ReportManager;
import com.progressoft.jip.payment.report.ReportSettings;
import com.progressoft.jip.payment.report.XMLReportGenerator;
import com.progressoft.jip.payment.report.ReportSettings.FileExtension;

public class ReportTestCases {
	private ReportGenerator generator = new ReportManager();
	private List<PaymentDTO> payments = new LinkedList<>();
	private AccountDTOImpl account = new AccountDTOImpl();
	private CustomerDTOImpl customer = new CustomerDTOImpl();
	private List<AccountDTO> accounts = new LinkedList<>();
	private IBANDTOImpl iban = new IBANDTOImpl();
	private PaymentPurpose purpose = new PurposeDTOImpl("shortCode", "purpose description");
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
		private PaymentPurpose paymentPurpose;

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

		public void setPaymentPurpose(PaymentPurpose paymentPurpose) {
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
		public Long getId() {
			return new Long(1);
		}

		@Override
		public String getBeneficiaryName() {
			return beneficiaryName;
		}

		@Override
		public PaymentPurpose getPaymentPurpose() {
			return purpose;
		}
	}

	private class PurposeDTOImpl implements PaymentPurpose {
		String shortCode;
		String description;

		public PurposeDTOImpl(String shortCode, String description) {
			super();
			this.shortCode = shortCode;
			this.description = description;
		}

		public String getShortCode() {
			return shortCode;
		}

		public String getDescription() {
			return description;
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
		account.setCustomerDTO(customer);

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
		payments.add(payment);
	}

	@Test(expected = NullPointerException.class)
	@Ignore
	public void callingGenerateReportMethod_WithNullParam_ShouldThrowNullPointerException() {
		 generator.generateReport(null);
	}

	@Test(expected = IllegalArgumentException.class)
	@Ignore
	public void callingGenerateReportMethod_WithEmptyParam_ShouldThrowIllegalArgumentException() {
	}

	@Test
	public void callingGenerateReportMethod_WithValidParam_ShouldGenerateReport() {
		generator.generateReport(
				new ReportSettings(payments, "C:/Users/u620/Desktop/Success", "report", FileExtension.XML));
	}

	@Ignore
	@Test
	public void Given_Action_Expected() {
	}
}