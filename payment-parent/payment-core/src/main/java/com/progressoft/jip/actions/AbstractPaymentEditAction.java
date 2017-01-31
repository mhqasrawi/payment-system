package com.progressoft.jip.actions;

import com.progressoft.jip.PaymentMenuContext;
import com.progressoft.jip.ui.action.Action;

/**
 * @param <T>
 * @author u612
 */
public abstract class AbstractPaymentEditAction<T> implements PaymentEditAction<T> {

    private PaymentAction action;

    protected void setAction(Action<PaymentMenuContext> paymentAction) {
        action = (PaymentAction) paymentAction;
    }

    public PaymentMenuContext doAction(PaymentMenuContext menuContext) {
        return action.doAction(menuContext);
    }

    public abstract T defaultValue(PaymentMenuContext context);

    public abstract void submitAction(PaymentMenuContext menuContext, T object);

}
