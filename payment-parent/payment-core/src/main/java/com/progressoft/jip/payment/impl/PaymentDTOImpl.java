package com.progressoft.jip.payment.impl;

import com.progressoft.jip.payment.PaymentDTO;
import com.progressoft.jip.payment.account.AccountDTO;
import com.progressoft.jip.payment.iban.IBANDTO;
import com.progressoft.jip.payment.purpose.PaymentPurposeDTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;

public class PaymentDTOImpl implements PaymentDTO {

	private int id;
	private AccountDTO orderingAccount;
	private IBANDTO beneficiaryIBAN;
	private String beneficiaryName;
	private BigDecimal paymentAmount;
	private Currency transferCurrency;
	private PaymentPurposeDTO paymentPurpose;
	private LocalDateTime settlementDate;
	private LocalDateTime creationDate;
	private PaymentState state;
	private PaymentStatus status;
	private String statusReason;

	public void setState(PaymentState state) {
		this.state = state;
	}

	public void setCreationDate(LocalDateTime creationDate) {
		this.creationDate = creationDate;
	}

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
	public LocalDateTime getSettlementDate() {
		return settlementDate;
	}

	public void setSettlementDate(LocalDateTime settlementDate) {
		this.settlementDate = settlementDate;
	}

	@Override
	public PaymentPurposeDTO getPaymentPurpose() {
		return paymentPurpose;
	}

	public void setPaymentPurpose(PaymentPurposeDTO paymentPurpose) {
		this.paymentPurpose = paymentPurpose;
	}

	@Override
	public PaymentState getState() {
		return state;
	}

	@Override
	public LocalDateTime getCreationDate() {
		return creationDate;
	}

	@Override
	public PaymentStatus getStatus() {
		return status;
	}

	public void setStatus(PaymentStatus status) {
		this.status = status;
	}

	@Override
	public String getStatusReason() {
		return statusReason;
	}

	public void setStatusReason(String statusReason) {
		this.statusReason = statusReason;
	}
}
