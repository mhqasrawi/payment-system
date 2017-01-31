package com.progressoft.jip.actions.forms;

import com.progressoft.jip.PaymentMenuContext;
import com.progressoft.jip.payment.account.AccountDTO;
import com.progressoft.jip.payment.account.AccountDTOConstant;
import com.progressoft.jip.payment.account.service.AccountPersistenceService;
import com.progressoft.jip.payment.usecase.EditAccountNameUseCase;
import com.progressoft.jip.ui.dynamic.menu.DefaultValueProvider;
import com.progressoft.jip.ui.dynamic.menu.SubmitAction;
import com.progressoft.jip.ui.field.StringField;
import com.progressoft.jip.ui.form.FormImpl;

import javax.inject.Inject;

public class EditAccountNameForm extends FormImpl<PaymentMenuContext, AccountDTO>
        implements DefaultValueProvider<PaymentMenuContext, AccountDTO>, SubmitAction<PaymentMenuContext, AccountDTO> {

    private static final String ENTER_ACCOUNT_NAME = "Enter Account New Name";
    private static final String EDIT_ACCOUNT_NAME_FORM_DESCRIPTION = "Edit Account Name";

    @Inject
    private AccountPersistenceService accountService;

    public EditAccountNameForm() {
        super(EDIT_ACCOUNT_NAME_FORM_DESCRIPTION);
    }

    public void init() {
        StringField accountNameField = new StringField();
        accountNameField.setDescription(ENTER_ACCOUNT_NAME);
        accountNameField.setName(AccountDTOConstant.ACCOUNT_NAME_ACCOUNT_DTO);
        this.addField(accountNameField);
    }

    @Override
    public void submitAction(PaymentMenuContext menuContext, AccountDTO accountDTO) {
        new EditAccountNameUseCase(accountService).process(menuContext, accountDTO);
    }

    @Override
    public AccountDTO defaultValue(PaymentMenuContext context) {
        return context.getCurrentAccount();
    }

    @Override
    public Class<AccountDTO> getClassType() {
        return AccountDTO.class;
    }

}
