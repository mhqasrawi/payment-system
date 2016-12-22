package com.progressoft.jip.currency.rate.provider.impl;

import java.math.BigDecimal;

import com.progressoft.jip.currency.rate.provider.CurrenyRateProvider;

public class CurrencyRateProviderImpl implements CurrenyRateProvider {

	@Override
	public BigDecimal get(String currency) {
		CurrencyRatesCSVReaderFilePathInjector csvReaderFilePathFileInjector = new CurrencyRatesCSVReaderFilePathInjector();
		return csvReaderFilePathFileInjector.getCurrencyRatesCsvReaderInstance("currency_rates.txt")
				.readRateFromCurrency(currency);
	}

}
