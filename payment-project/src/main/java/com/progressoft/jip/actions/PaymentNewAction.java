package com.progressoft.jip.actions;

import com.progressoft.jip.PaymentMenuContext;
import com.progressoft.jip.ui.dynamic.menu.SubmitAction;

/**
 * @author Ahmad.Jardat
 *
 */
public interface PaymentNewAction<T> extends PaymentAction,  SubmitAction<PaymentMenuContext, T>{ 

}
