package com.progressoft.jip.actions;

import java.util.function.Function;

import com.progressoft.jip.PaymentMenuContext;

public interface PaymentEditAction<T> extends Function<PaymentMenuContext, T>, PaymentNewAction<T> {

}
