package com.progressoft.jip.actions.forms;

import com.progressoft.jip.PaymentMenuContext;
import com.progressoft.jip.payment.Payment;
import com.progressoft.jip.payment.PaymentBuilder;
import com.progressoft.jip.payment.PaymentDAO;
import com.progressoft.jip.payment.PaymentDTO.PaymentStatus;
import com.progressoft.jip.payment.PaymentInfo;
import com.progressoft.jip.payment.account.AccountDTO;
import com.progressoft.jip.payment.account.dao.AccountDAO;
import com.progressoft.jip.payment.iban.IBANDTO;
import com.progressoft.jip.payment.iban.IBANValidator;
import com.progressoft.jip.payment.iban.dao.IBANDAO;
import com.progressoft.jip.payment.purpose.PaymentPurposeDTO;
import com.progressoft.jip.payment.purpose.dao.impl.PaymentPurposeDAO;
import com.progressoft.jip.payment.usecase.NewPaymentUseCase;
import com.progressoft.jip.ui.dynamic.menu.DefaultValueProvider;
import com.progressoft.jip.ui.dynamic.menu.SubmitAction;
import com.progressoft.jip.ui.field.*;
import com.progressoft.jip.ui.form.FormImpl;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;

public class NewPaymentForm extends FormImpl<PaymentMenuContext, PaymentInfo> implements
        SubmitAction<PaymentMenuContext, PaymentInfo>, DefaultValueProvider<PaymentMenuContext, PaymentInfo> {
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
    private IBANValidator ibanValidator;
    @Inject
    private PaymentPurposeDAO paymentPurposeDao;
    @Inject
    private PaymentBuilder paymentBuilder;
    @Inject
    private IBANDAO ibanDao;
    @Inject
    private PaymentDAO paymentDao;
    @Inject
    private AccountDAO accountDAO;

    public NewPaymentForm() {
        super(NEW_PAYMENT_INFO);
    }

    public void init() {
        this.addField(new IBANField(ibanValidator).setDescription(ENTER_BENFICARY_IBAN).setName(BENEFICIARY_IBAN));
        this.addField(new StringField().setName(BENEFICIARY_NAME).setDescription(ENTER_BENEFICIARY_NAME));
        this.addField(new BigDecimalField().setName(PAYMENT_AMOUNT).setDescription(ENTER_PAYMENT_AMOUNT));
        this.addField(new CurrencyField().setName(TRANSFER_CURRENCY).setDescription(ENTER_TRANSFER_CURRENCY));
        this.addField(new PaymentPurposeField(paymentPurposeDao).setName(PAYMENT_PURPOSE)
                .setDescription(ENTER_PAYMENT_PURPOSE_SHORT_CODE));
    }

    @Override
    public void submitAction(PaymentMenuContext menuContext, PaymentInfo paymentInfo) {
        Payment newPayment = paymentBuilder.getNewPayment(paymentInfo);
        newPayment.doAction(new NewPaymentUseCase(paymentDao, ibanDao, accountDAO));
    }

    @Override
    public PaymentInfo defaultValue(PaymentMenuContext context) {
        return new PaymentInfoImpl(context);
    }

    @Override
    public Class<PaymentInfo> getClassType() {
        return PaymentInfo.class;
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
        public PaymentPurposeDTO getPaymentPurpose() {
            return null;
        }

		@Override
		public LocalDateTime getSettlementDate() {
			return null;
		}

		@Override
		public PaymentStatus getStatus() {
			return null;
		}

		@Override
		public LocalDateTime getCreationDate() {
			return null;
		}
    }
}
