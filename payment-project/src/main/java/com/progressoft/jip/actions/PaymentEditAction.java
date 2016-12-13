package com.progressoft.jip.actions;

import com.progressoft.jip.PaymentMenuContext;
import com.progressoft.jip.ui.dynamic.menu.DefaultValueProvider;

public interface PaymentEditAction<T> extends DefaultValueProvider<PaymentMenuContext, T>, PaymentNewAction<T> {

}
