package com.progressoft.jip.payment.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.Date;

import com.progressoft.jip.payment.PaymentDTO;
import com.progressoft.jip.payment.account.AccountDTO;
import com.progressoft.jip.payment.iban.IBANDTO;
import com.progressoft.jip.payment.purpose.PaymentPurposeDTO;

public class PaymentDTOImpl implements PaymentDTO {

	private int id;
	private AccountDTO orderingAccount;
	private IBANDTO beneficiaryIBAN;
	private String beneficiaryName;
	private BigDecimal paymentAmount;
	private Currency transferCurrency;
	private LocalDateTime  paymentDate;
	private PaymentPurposeDTO paymentPurpose;

	public PaymentDTOImpl() {
	}

	@Override
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public AccountDTO getOrderingAccount() {
		return orderingAccount;
	}

	public void setOrderingAccount(AccountDTO orderingAccount) {
		this.orderingAccount = orderingAccount;
	}

	@Override
	public IBANDTO getBeneficiaryIBAN() {
		return beneficiaryIBAN;
	}

	public void setBeneficiaryIBAN(IBANDTO beneficiaryIBAN) {
		this.beneficiaryIBAN = beneficiaryIBAN;
	}

	@Override
	public String getBeneficiaryName() {
		return beneficiaryName;
	}

	public void setBeneficiaryName(String beneficiaryName) {
		this.beneficiaryName = beneficiaryName;
	}

	@Override
	public BigDecimal getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(BigDecimal paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	@Override
	public Currency getTransferCurrency() {
		return transferCurrency;
	}

	public void setTransferCurrency(Currency transferCurrency) {
		this.transferCurrency = transferCurrency;
	}

	@Override
	public LocalDateTime getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(LocalDateTime paymentDate) {
		this.paymentDate = paymentDate;
	}

	@Override
	public PaymentPurposeDTO getPaymentPurpose() {
		return paymentPurpose;
	}

	public void setPaymentPurpose(PaymentPurposeDTO paymentPurpose) {
		this.paymentPurpose = paymentPurpose;
	}

}
