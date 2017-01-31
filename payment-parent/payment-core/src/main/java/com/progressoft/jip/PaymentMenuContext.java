package com.progressoft.jip;

import com.progressoft.jip.payment.account.AccountDTO;
import com.progressoft.jip.ui.menu.MenuContext;

public interface PaymentMenuContext extends MenuContext {


    public static final String CURRENT_ACCOUNT_DTO = "ACCOUNT_DTO";

    AccountDTO getCurrentAccount();

    void setCurrentAccount(AccountDTO account);

}
