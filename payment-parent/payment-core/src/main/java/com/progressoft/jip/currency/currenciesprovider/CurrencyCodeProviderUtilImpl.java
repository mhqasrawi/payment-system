package com.progressoft.jip.currency.currenciesprovider;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Set;

/**
 * Created by u623 on 23/01/2017.
 */
public class CurrencyCodeProviderUtilImpl implements CurrencyCodeProvider {
    private List<String> currencyCodeList = new ArrayList<>();

    @Override
    public Iterable<String> listAllCurrency() {
        Set<Currency> availableCurrencies = Currency.getAvailableCurrencies();
        for (Currency currency : availableCurrencies) {
            currencyCodeList.add(currency.getCurrencyCode());
        }
        return currencyCodeList;
    }
}
