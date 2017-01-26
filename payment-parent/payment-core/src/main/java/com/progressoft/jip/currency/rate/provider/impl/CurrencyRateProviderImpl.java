package com.progressoft.jip.currency.rate.provider.impl;

import com.progressoft.jip.currency.rate.provider.CurrenyRateProvider;

import java.math.BigDecimal;

public class CurrencyRateProviderImpl implements CurrenyRateProvider {

    @Override
    public BigDecimal get(String currency) {
        CurrencyRatesCSVReaderFilePathInjector csvReaderFilePathFileInjector = new CurrencyRatesCSVReaderFilePathInjector();
        return csvReaderFilePathFileInjector.getCurrencyRatesCsvReaderInstance("currency_rates.txt")
                .readRateFromCurrency(currency);
    }

}
