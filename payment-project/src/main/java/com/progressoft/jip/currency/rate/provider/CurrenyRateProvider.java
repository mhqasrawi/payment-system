package com.progressoft.jip.currency.rate.provider;

import java.math.BigDecimal;

public interface CurrenyRateProvider {

	BigDecimal get(String currency);

}
