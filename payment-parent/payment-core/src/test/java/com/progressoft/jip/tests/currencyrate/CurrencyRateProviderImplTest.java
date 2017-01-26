package com.progressoft.jip.tests.currencyrate;

import com.progressoft.jip.currency.rate.provider.CurrenyRateProvider;
import com.progressoft.jip.currency.rate.provider.impl.CurrencyRateProviderImpl;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CurrencyRateProviderImplTest {
    @Test
    public void currencyRateProviderImplNotNull() throws Exception {
        CurrenyRateProvider currenyRateProvider = new CurrencyRateProviderImpl();
        assertNotNull(currenyRateProvider);
    }

    @Test
    public void givenCurrencyJODGetConversionRate_1_4130() throws Exception {
        CurrenyRateProvider currenyRateProvider = new CurrencyRateProviderImpl();
        assertEquals(currenyRateProvider.get("JOD"), BigDecimal.valueOf(1.4130));
    }

    @Test
    public void givenCurrencyMYRGetConversionRate_0_2282() throws Exception {
        CurrenyRateProvider currenyRateProvider = new CurrencyRateProviderImpl();
        assertEquals(currenyRateProvider.get("MYR"), BigDecimal.valueOf(0.2282));
    }

    @Test
    public void givenCurrencyJPYGetConversionRate_0_0090() throws Exception {
        CurrenyRateProvider currenyRateProvider = new CurrencyRateProviderImpl();
        assertEquals(currenyRateProvider.get("JPY"), BigDecimal.valueOf(0.0090));
    }
}
