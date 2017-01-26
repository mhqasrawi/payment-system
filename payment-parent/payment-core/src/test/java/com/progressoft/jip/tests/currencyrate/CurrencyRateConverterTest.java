package com.progressoft.jip.tests.currencyrate;

import com.progressoft.jip.currency.converter.CurrencyRateConverter;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CurrencyRateConverterTest {

    @Test
    public void currencyRateConverterNotNull() throws Exception {
        CurrencyRateConverter currencyRateConverter = new CurrencyRateConverter() {
            @Override
            public BigDecimal convert(String from, String to, double amount) {
                return null;
            }
        };
        assertNotNull(currencyRateConverter);
    }

    @Test
    public void givenCurrencyFromAndCurrencyToAndAmountConvert() throws Exception {
        CurrencyRateConverter currencyRateConverter = new CurrencyRateConverter() {
            @Override
            public BigDecimal convert(String from, String to, double amount) {
                return BigDecimal.valueOf(200.34);
            }
        };
        assertEquals(currencyRateConverter.convert("JOD", "USD", 300.69), BigDecimal.valueOf(200.34));
    }
}
