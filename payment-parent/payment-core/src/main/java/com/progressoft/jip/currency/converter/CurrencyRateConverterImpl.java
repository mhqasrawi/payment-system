package com.progressoft.jip.currency.converter;

import com.progressoft.jip.currency.rate.provider.CurrenyRateProvider;
import com.progressoft.jip.currency.rate.provider.impl.CurrencyRateProviderImpl;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CurrencyRateConverterImpl implements CurrencyRateConverter {

    @Override
    public BigDecimal convert(String from, String to, double amount) {
        CurrenyRateProvider currencyRateProvider = new CurrencyRateProviderImpl();
        BigDecimal rateFrom = currencyRateProvider.get(from);
        BigDecimal rateTo = currencyRateProvider.get(to);
        return rateFrom.multiply(BigDecimal.valueOf(amount)).divide(rateTo, RoundingMode.HALF_UP);
    }
}
