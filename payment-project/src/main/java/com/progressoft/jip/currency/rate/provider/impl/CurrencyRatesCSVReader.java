package com.progressoft.jip.currency.rate.provider.impl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;

public class CurrencyRatesCSVReader {
	private String filePath;

	public CurrencyRatesCSVReader(String filePath) {
		this.filePath = filePath;
	}

	public String getFilePath() {
		return filePath;
	}

	public BigDecimal readRateFromCurrency(String currency) {
		try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
			String line = searchForCurrenyRate(currency, bufferedReader);
			if (line != null) {
				return BigDecimal.valueOf(Double.valueOf(line.split(",")[1]));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private String searchForCurrenyRate(String currency, BufferedReader bufferedReader) throws IOException {
		String line;
		while ((line = bufferedReader.readLine()) != null) {
			if (line.contains(currency)) {
				break;
			}
		}
		return line;
	}

}
