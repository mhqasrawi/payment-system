package com.progressoft.jip.payment.account;

import com.progressoft.jip.payment.customer.CustomerDTO;
import com.progressoft.jip.payment.iban.IBANDTO;

import java.math.BigDecimal;
import java.util.Currency;

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

    public void setIbandto(IBANDTO ibandto) {
        this.ibandto = ibandto;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    @Override
    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getAccountNumber() {
        return this.accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    @Override
    public IBANDTO getIban() {
        return this.ibandto;
    }

    @Override
    public String getAccountName() {
        return this.accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    @Override
    public Currency getCurreny() {
        return this.currency;
    }

    @Override
    public AccountStatus getAccountStatus() {
        return this.accountStatus;
    }

    public void setAccountStatus(AccountStatus accountStatus) {
        this.accountStatus = accountStatus;
    }

    @Override
    public CustomerDTO getCustomerDTO() {
        return this.customerDTO;
    }

    public void setCustomerDTO(CustomerDTO customerDTO) {
        this.customerDTO = customerDTO;
    }

    @Override
    public int getIbanId() {
        return this.ibanId;
    }

    public void setIbanId(int ibanId) {
        this.ibanId = ibanId;
    }

    @Override
    public String getPaymentRule() {
        return paymentRule;
    }

    public void setPaymentRule(String paymentRule) {
        this.paymentRule = paymentRule;
    }

    @Override
    public String getPaymentRuleInfo() {
        return paymentRuleInfo;
    }

    public void setPaymentRuleInfo(String paymentRuleInfo) {
        this.paymentRuleInfo = paymentRuleInfo;
    }

    @Override
    public BigDecimal getBalance() {
        synchronized (balanceMonitor) {
            return this.balance;
        }
    }

    public void setBalance(BigDecimal value) {
        synchronized (balanceMonitor) {
            this.balance = value;
        }
    }
}