package com.progressoft.jip.currency.converter;

import java.math.BigDecimal;

public interface CurrencyRateConverter {

	BigDecimal convert(String from, String to, double amount);

}
