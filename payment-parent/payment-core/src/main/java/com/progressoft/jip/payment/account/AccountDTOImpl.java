package com.progressoft.jip.payment.account;

import java.math.BigDecimal;
import java.util.Currency;

import com.progressoft.jip.payment.customer.CustomerDTO;
import com.progressoft.jip.payment.iban.IBANDTO;

public class AccountDTOImpl implements AccountDTO {
	private final Object balanceMonitor = new Object();
	private int id;
	private String accountNumber;
	private IBANDTO ibandto;
	private String accountName;
	private Currency currency;
	private AccountStatus accountStatus;
	private CustomerDTO customerDTO;
	private int ibanId;
	private String paymentRule;
	private String paymentRuleInfo;
	private BigDecimal balance = BigDecimal.ZERO;

	public AccountDTOImpl() {
	}

	public AccountDTOImpl(AccountDTO accountDTO) {
		this.setAccountName(accountDTO.getAccountName());
		this.setAccountNumber(accountDTO.getAccountNumber());
		this.setAccountStatus(accountDTO.getAccountStatus());
		this.setCurrency(accountDTO.getCurreny());
		this.setIbandto(accountDTO.getIban());

		this.setId(accountDTO.getId());
		this.setCustomerDTO(accountDTO.getCustomerDTO());
		this.setIbanId(accountDTO.getIbanId());
		this.setPaymentRule(accountDTO.getPaymentRule());
		this.setPaymentRuleInfo(accountDTO.getPaymentRuleInfo());
		this.setBalance(accountDTO.getBalance());
	}

	public void setCustomerDTO(CustomerDTO customerDTO) {
		this.customerDTO = customerDTO;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public void setIbandto(IBANDTO ibandto) {
		this.ibandto = ibandto;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public void setAccountStatus(AccountStatus accountStatus) {
		this.accountStatus = accountStatus;
	}

	public void setPaymentRule(String paymentRule) {
		this.paymentRule = paymentRule;
	}

	public void setPaymentRuleInfo(String paymentRuleInfo) {
		this.paymentRuleInfo = paymentRuleInfo;
	}

	public void setBalance(BigDecimal value) {
		synchronized (balanceMonitor) {
			this.balance = value;
		}
	}

	@Override
	public int getId() {
		return this.id;
	}

	@Override
	public String getAccountNumber() {
		return this.accountNumber;
	}

	@Override
	public IBANDTO getIban() {
		return this.ibandto;
	}

	@Override
	public String getAccountName() {
		return this.accountName;
	}

	@Override
	public Currency getCurreny() {
		return this.currency;
	}

	@Override
	public AccountStatus getAccountStatus() {
		return this.accountStatus;
	}

	@Override
	public CustomerDTO getCustomerDTO() {
		return this.customerDTO;
	}

	public void setIbanId(int ibanId) {
		this.ibanId = ibanId;
	}

	@Override
	public int getIbanId() {
		return this.ibanId;
	}

	@Override
	public String getPaymentRule() {
		return paymentRule;
	}

	@Override
	public String getPaymentRuleInfo() {
		return paymentRuleInfo;
	}

	@Override
	public BigDecimal getBalance() {
		synchronized (balanceMonitor) {
			return this.balance;
		}
	}
}