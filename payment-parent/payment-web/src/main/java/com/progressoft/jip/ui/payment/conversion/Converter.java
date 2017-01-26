package com.progressoft.jip.ui.payment.conversion;

/**
 * Created by u624 on 1/26/2017.
 */
@FunctionalInterface
public interface Converter<T, J> {
    T convert(J j);
}
