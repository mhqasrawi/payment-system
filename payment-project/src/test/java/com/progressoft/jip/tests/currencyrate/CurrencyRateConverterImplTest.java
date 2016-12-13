package com.progressoft.jip.tests.currencyrate;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Test;

import com.progressoft.jip.currency.converter.CurrencyRateConverter;
import com.progressoft.jip.currency.converter.CurrencyRateConverterImpl;

public class CurrencyRateConverterImplTest {

	@Test
	public void currencyRateConverterImplNotNull() throws Exception {
		CurrencyRateConverter currencyRateConverter = new CurrencyRateConverterImpl();
		assertNotNull(currencyRateConverter);
	}

	@Test
	public void givenFromCurrencyAndToCurrencyAndAmountConvert() throws Exception {
		CurrencyRateConverter currencyRateConverter = new CurrencyRateConverterImpl();
		assertEquals(currencyRateConverter.convert("JOD", "KWD", 100), BigDecimal.valueOf(43.0255));

	}
}
