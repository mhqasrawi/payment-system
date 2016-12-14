package com.progressoft.jip.actions.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;

import javax.inject.Inject;

import com.progressoft.jip.PaymentMenuContext;
import com.progressoft.jip.actions.AbstractPaymentEditAction;
import com.progressoft.jip.actions.PaymentDynamicFormActionBuilder;
import com.progressoft.jip.payment.Payment;
import com.progressoft.jip.payment.PaymentBuilder;
import com.progressoft.jip.payment.PaymentInfo;
import com.progressoft.jip.payment.account.AccountDTO;
import com.progressoft.jip.payment.iban.IBANDTO;
import com.progressoft.jip.payment.iban.IBANValidator;
import com.progressoft.jip.payment.iban.dao.IBANDAO;
import com.progressoft.jip.payment.purpose.PaymentPurposeDTO;
import com.progressoft.jip.payment.purpose.dao.impl.PaymentPurposeDAO;
import com.progressoft.jip.ui.field.BigDecimalField;
import com.progressoft.jip.ui.field.CurrencyField;
import com.progressoft.jip.ui.field.IBANField;
import com.progressoft.jip.ui.field.PaymentPurposeField;
import com.progressoft.jip.ui.field.StringField;
import com.progressoft.jip.ui.form.Form;
import com.progressoft.jip.ui.form.FormImpl;

public class NewPaymentAction extends AbstractPaymentEditAction<PaymentInfo> {

	private static final String ENTER_TRANSFER_CURRENCY = "Enter Transfer Currency";
	private static final String ENTER_PAYMENT_AMOUNT = "Enter Payment Amount";
	private static final String BENEFICIARY_IBAN = "beneficiaryIBAN";
	private static final String ENTER_BENEFICIARY_NAME = "Enter Beneficiary Name";
	private static final String ENTER_BENFICARY_IBAN = "Enter Benficary Iban";
	private static final String NEW_PAYMENT_INFO = "New Payment Info";
	private static final String BENEFICIARY_NAME = "beneficiaryName";
	private static final String PAYMENT_AMOUNT = "paymentAmount";
	private static final String TRANSFER_CURRENCY = "transferCurrency";
	private static final String PAYMENT_PURPOSE = "paymentPurpose";
	private static final String ENTER_PAYMENT_PURPOSE_SHORT_CODE = "Enter Payment Purpose Short Code";

	@Inject
	private PaymentDynamicFormActionBuilder<PaymentInfo> dynamicFormActionBuilder;
	@Inject
	private IBANValidator ibanValidator;
	@Inject
	private PaymentPurposeDAO paymentDao;
	@Inject
	private PaymentBuilder paymentBuilder;
	@Inject
	private IBANDAO ibandao;

	public void init() {
		setAction(dynamicFormActionBuilder.setInterfaceType(PaymentInfo.class).setDefaultObjectStrategy(this)
				.setForm(getForm()).setSubmitAction(this).build());

	}

	private Form getForm() {
		FormImpl form = new FormImpl(NEW_PAYMENT_INFO);
		form.addField(new IBANField(ibanValidator).setDescription(ENTER_BENFICARY_IBAN).setName(BENEFICIARY_IBAN));
		form.addField(new StringField().setName(BENEFICIARY_NAME).setDescription(ENTER_BENEFICIARY_NAME));
		form.addField(new BigDecimalField().setName(PAYMENT_AMOUNT).setDescription(ENTER_PAYMENT_AMOUNT));
		form.addField(new CurrencyField().setName(TRANSFER_CURRENCY).setDescription(ENTER_TRANSFER_CURRENCY));
		form.addField(new PaymentPurposeField(paymentDao).setName(PAYMENT_PURPOSE)
				.setDescription(ENTER_PAYMENT_PURPOSE_SHORT_CODE));
		return form;
	}

	@Override
	public PaymentInfo defaultValue(PaymentMenuContext context) {
		return new PaymentInfoImpl(context);
	}

	@Override
	public void submitAction(PaymentMenuContext menuContext, PaymentInfo paymentInfo) {
		Payment newPayment = paymentBuilder.getNewPayment(paymentInfo);
		newPayment.commitPayment();
	}

	private static class PaymentInfoImpl implements PaymentInfo {

		private AccountDTO orderingAccount;

		public PaymentInfoImpl(PaymentMenuContext context) {
			this.orderingAccount = context.getCurrentAccount();
		}

		@Override
		public AccountDTO getOrderingAccount() {
			return orderingAccount;
		}

		@Override
		public IBANDTO getBeneficiaryIBAN() {
			return null;
		}

		@Override
		public String getBeneficiaryName() {
			return null;
		}

		@Override
		public BigDecimal getPaymentAmount() {
			return null;
		}

		@Override
		public Currency getTransferCurrency() {
			return null;
		}

		@Override
		public LocalDateTime getPaymentDate() {
			return LocalDateTime.now();
		}

		@Override
		public PaymentPurposeDTO getPaymentPurpose() {
			return null;
		}
	}

}
