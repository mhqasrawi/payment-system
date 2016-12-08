package com.progressoft.jip.payment;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;

import com.progressoft.jip.payment.account.AccountDTO;
import com.progressoft.jip.payment.iban.DTO;
import com.progressoft.jip.payment.iban.IBANDTO;

public interface PaymentDTO extends DTO {

    AccountDTO getOrderingAccount();

    IBANDTO getBeneficiaryIBAN();

    String getBeneficiaryName();

    BigDecimal getPaymentAmount();

    Currency getTransferCurrency();

    LocalDateTime getPaymentDate();

    PaymentPurposeDTO getPaymentPurpose();
}
