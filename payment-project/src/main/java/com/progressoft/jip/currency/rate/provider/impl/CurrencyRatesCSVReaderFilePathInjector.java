package com.progressoft.jip.currency.rate.provider.impl;

import java.io.File;
import java.nio.file.Paths;

public class CurrencyRatesCSVReaderFilePathInjector {

	public CurrencyRatesCSVReader getCurrencyRatesCsvReaderInstance(String fileName) {
		return new CurrencyRatesCSVReader(getFilePathFromFileName(fileName));
	}

	private String getFilePathFromFileName(String fileName) {
		return Paths.get(getResourcesPath()).resolve(fileName)
				.toAbsolutePath().toString();
	}

	private String getResourcesPath() {
		return new File("src/main/resources").getAbsolutePath();
	}

}
