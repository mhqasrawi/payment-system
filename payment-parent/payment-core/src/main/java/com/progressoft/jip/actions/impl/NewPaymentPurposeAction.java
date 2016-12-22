package com.progressoft.jip.actions.impl;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Configurable;

import com.progressoft.jip.PaymentMenuContext;
import com.progressoft.jip.actions.AbstractPaymentNewAction;
import com.progressoft.jip.actions.PaymentDynamicFormActionBuilder;
import com.progressoft.jip.actions.PaymentNewAction;
import com.progressoft.jip.payment.purpose.PaymentPurposeDTO;
import com.progressoft.jip.payment.purpose.dao.impl.PaymentPurposeDAO;
import com.progressoft.jip.ui.field.StringField;
import com.progressoft.jip.ui.form.Form;
import com.progressoft.jip.ui.form.FormImpl;

public class NewPaymentPurposeAction extends AbstractPaymentNewAction<PaymentPurposeDTO>
		implements PaymentNewAction<PaymentPurposeDTO> {

	private static final String NEW_PAYMENT_PURPOSE = "New Payment Purpose";

	@Inject
	private PaymentDynamicFormActionBuilder<PaymentPurposeDTO> dynamicFormActionBuilder;
	@Inject
	private PaymentPurposeDAO paymentPurposeDAO;

	public void init() {
		setAction(dynamicFormActionBuilder.setInterfaceType(PaymentPurposeDTO.class).setSubmitAction(this)
				.setForm(getNewPaymentPurposeForm()).build());
	}

	private Form getNewPaymentPurposeForm() {
		return new FormImpl(NEW_PAYMENT_PURPOSE)
				.addField(new StringField().setName("shortCode").setDescription("Enter Short Code"))
				.addField(new StringField().setName("description").setDescription("Enter Payment Purpose Description"));
	}

	@Override
	public void submitAction(PaymentMenuContext menuContext, PaymentPurposeDTO paymentPurpose) {
		paymentPurposeDAO.save(paymentPurpose);
	}

}