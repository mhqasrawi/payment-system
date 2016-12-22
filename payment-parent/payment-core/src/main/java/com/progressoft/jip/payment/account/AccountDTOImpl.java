package com.progressoft.jip.payment.account;

import java.util.Currency;

import com.progressoft.jip.payment.customer.CustomerDTO;
import com.progressoft.jip.payment.iban.IBANDTO;

public class AccountDTOImpl implements AccountDTO {

	private int id;
	private String accountNumber;
	private IBANDTO ibandto;
	private String accountName;
	private Currency currency;
	private AccountStatus accountStatus;
	private CustomerDTO customerDTO;
	private int ibanId;

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

	public int getId() {
		return this.id;
	}

	public String getAccountNumber() {
		return this.accountNumber;
	}

	public IBANDTO getIban() {
		return this.ibandto;
	}

	public String getAccountName() {
		return this.accountName;
	}

	public Currency getCurreny() {
		return this.currency;
	}

	public AccountStatus getAccountStatus() {
		return this.accountStatus;
	}

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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPaymentRuleInfo() {
		// TODO Auto-generated method stub
		return null;
	}

}
