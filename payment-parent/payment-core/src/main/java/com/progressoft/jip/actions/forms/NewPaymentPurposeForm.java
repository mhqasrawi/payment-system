package com.progressoft.jip.actions.forms;

import com.progressoft.jip.PaymentMenuContext;
import com.progressoft.jip.payment.purpose.PaymentPurposeDTO;
import com.progressoft.jip.payment.purpose.dao.impl.PaymentPurposeDAO;
import com.progressoft.jip.payment.usecase.NewPaymentPurposeUseCase;
import com.progressoft.jip.ui.dynamic.menu.SubmitAction;
import com.progressoft.jip.ui.field.StringField;
import com.progressoft.jip.ui.form.FormImpl;

import javax.inject.Inject;

public class NewPaymentPurposeForm extends FormImpl<PaymentMenuContext, PaymentPurposeDTO>
        implements SubmitAction<PaymentMenuContext, PaymentPurposeDTO> {

    private static final String NEW_PAYMENT_PURPOSE = "New Payment Purpose";

    @Inject
    private PaymentPurposeDAO paymentPurposeDAO;

    public NewPaymentPurposeForm() {
        super(NEW_PAYMENT_PURPOSE);
    }

    public void init() {
        this.addField(new StringField().setName("shortCode").setDescription("Enter Short Code"));
        this.addField(new StringField().setName("description").setDescription("Enter Payment Purpose Description"));
    }

    @Override
    public void submitAction(PaymentMenuContext menuContext, PaymentPurposeDTO paymentPurpose) {
        new NewPaymentPurposeUseCase(paymentPurposeDAO).process(paymentPurpose);
    }

    @Override
    public Class<PaymentPurposeDTO> getClassType() {
        return PaymentPurposeDTO.class;
    }

}
