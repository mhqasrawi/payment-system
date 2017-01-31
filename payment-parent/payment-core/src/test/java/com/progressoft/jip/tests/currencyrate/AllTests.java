package com.progressoft.jip.tests.currencyrate;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({CurrenyRateProviderTest.class, CurrencyRateConverterTest.class, CurrencyRateProviderImplTest.class,
        CurrencyRateConverterTest.class, CurrencyRateConverterImplTest.class,
        CurrencyRatesCSVReaderFilePathInjectorTest.class})
public class AllTests {

}
