package com.progressoft.jip.tests.currencyrate;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Test;

import com.progressoft.jip.currency.rate.provider.CurrenyRateProvider;

public class CurrenyRateProviderTest {

	@Test
	public void currencyRateProviderNotNull() throws Exception {
		CurrenyRateProvider currencyRateProvider = new CurrenyRateProvider() {
			@Override
			public BigDecimal get(String currency) {
				return null;
			}
		};

		assertNotNull(currencyRateProvider);
	}

	@Test
	public void givenCurrencyGetCurrencyRate() throws Exception {
		CurrenyRateProvider currencyRateProvider = new CurrenyRateProvider() {
			@Override
			public BigDecimal get(String currency) {
				return BigDecimal.valueOf(0.7);
			}
		};

		assertEquals(currencyRateProvider.get("JOD"), BigDecimal.valueOf(0.7));
	}

}
