package com.progressoft.jip.payment;

import com.progressoft.jip.payment.account.AccountDTO;
import com.progressoft.jip.payment.iban.DTO;
import com.progressoft.jip.payment.iban.IBANDTO;
import com.progressoft.jip.payment.purpose.PaymentPurposeDTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;

public interface PaymentDTO extends DTO {

    AccountDTO getOrderingAccount();

    IBANDTO getBeneficiaryIBAN();

    String getBeneficiaryName();

    BigDecimal getPaymentAmount();

    Currency getTransferCurrency();

    LocalDateTime getPaymentDate();

    PaymentPurposeDTO getPaymentPurpose();
}
