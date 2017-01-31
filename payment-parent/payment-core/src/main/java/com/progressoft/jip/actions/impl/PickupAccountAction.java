package com.progressoft.jip.actions.impl;

import com.progressoft.jip.PaymentMenuContext;
import com.progressoft.jip.actions.PaymentAction;
import com.progressoft.jip.actions.PaymentDynamicFormActionBuilder;
import com.progressoft.jip.actions.forms.PickupAccountForm;
import com.progressoft.jip.ui.action.Action;
import com.progressoft.jip.ui.dynamic.menu.preemtivewrapper.StringContainer;

import javax.inject.Inject;

public class PickupAccountAction implements PaymentAction {


    @Inject
    private PaymentDynamicFormActionBuilder<StringContainer> dynamicFormActionBuilder;

    private PickupAccountForm accountForm = new PickupAccountForm();


    @Override
    public PaymentMenuContext doAction(PaymentMenuContext menuContext) {
        Action<PaymentMenuContext> action = dynamicFormActionBuilder.refresh().setInterfaceType(StringContainer.class).setSubmitAction(accountForm)
                .setForm(accountForm).build();
        return action.doAction(menuContext);
    }

}
