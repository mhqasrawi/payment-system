package com.progressoft.jip.actions;

import com.progressoft.jip.PaymentMenuContext;
import com.progressoft.jip.ui.action.Action;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Configurable;

/**
 * @author u612
 */
@Configurable(autowire = Autowire.BY_TYPE)
public abstract class AbstractPaymentNewAction<T> implements PaymentNewAction<T> {


    private PaymentAction action;

    protected void setAction(Action<PaymentMenuContext> paymentAction) {
        action = (PaymentAction) paymentAction;
    }

    public PaymentMenuContext doAction(PaymentMenuContext menuContext) {
        return action.doAction(menuContext);
    }

    public abstract void submitAction(PaymentMenuContext menuContext, T object);

}
