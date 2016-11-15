package com.progressoft.jip.payment.customer;

import com.progressoft.jip.payment.account.AccountDTO;
import com.progressoft.jip.payment.iban.DTO;

public interface CustomerDTO extends DTO {

    String getName();

    Iterable<AccountDTO> getAccounts();
}
