package com.progressoft.jip.actions.impl;

import com.progressoft.jip.PaymentMenuContext;
import com.progressoft.jip.actions.PaymentAction;
import com.progressoft.jip.actions.PaymentDynamicFormActionBuilder;
import com.progressoft.jip.actions.forms.EditAccountNameForm;
import com.progressoft.jip.payment.account.AccountDTO;

import javax.inject.Inject;

/**
 * @author Ahmad.Jardat
 */
public class EditAccountNameAction implements PaymentAction {

    @Inject
    private PaymentDynamicFormActionBuilder<AccountDTO> dynamicFormActionBuilder;

    private EditAccountNameForm editAccountNameForm = new EditAccountNameForm();


    @Override
    public PaymentMenuContext doAction(PaymentMenuContext menuContext) {
        PaymentMenuContext doAction = dynamicFormActionBuilder.refresh().setDefaultObjectStrategy(editAccountNameForm).setInterfaceType(AccountDTO.class)
                .setSubmitAction(editAccountNameForm).setForm(editAccountNameForm).build().doAction(menuContext);
        return doAction;
    }
}
