package com.progressoft.jip.actions.impl;

import com.progressoft.jip.PaymentMenuContext;
import com.progressoft.jip.actions.PaymentAction;
import com.progressoft.jip.actions.PaymentDynamicFormActionBuilder;
import com.progressoft.jip.actions.forms.NewPaymentPurposeForm;
import com.progressoft.jip.payment.purpose.PaymentPurposeDTO;
import com.progressoft.jip.ui.action.Action;

import javax.inject.Inject;

public class NewPaymentPurposeAction implements PaymentAction {

    @Inject
    private PaymentDynamicFormActionBuilder<PaymentPurposeDTO> dynamicFormActionBuilder;

    private NewPaymentPurposeForm newPaymentPurposeForm = new NewPaymentPurposeForm();

    @Override
    public PaymentMenuContext doAction(PaymentMenuContext menuContext) {
        Action<PaymentMenuContext> action = dynamicFormActionBuilder.refresh().setInterfaceType(PaymentPurposeDTO.class)
                .setSubmitAction(newPaymentPurposeForm).setForm(newPaymentPurposeForm).build();
        return action.doAction(menuContext);
    }

}