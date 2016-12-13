package com.progressoft.jip.tests.currencyrate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;

import com.progressoft.jip.currency.rate.provider.impl.CurrencyRatesCSVReader;
import com.progressoft.jip.currency.rate.provider.impl.CurrencyRatesCSVReaderFilePathInjector;

public class CurrencyRatesCSVReaderFilePathInjectorTest {

	@Test
	public void currencyRatesCsvFileInjectorNotNull() throws Exception {
		CurrencyRatesCSVReaderFilePathInjector csvFileInjector = new CurrencyRatesCSVReaderFilePathInjector();
		assertNotNull(csvFileInjector);
	}

	@Test
	public void givenFileNameReturnInstanceOfCurrencyRateCsvReader() throws Exception {
		CurrencyRatesCSVReaderFilePathInjector csvFileInjector = new CurrencyRatesCSVReaderFilePathInjector();
		CurrencyRatesCSVReader currencyRatesCSVReader = csvFileInjector
				.getCurrencyRatesCsvReaderInstance("currency_rates.txt");
		assertEquals(Paths.get(currencyRatesCSVReader.getFilePath()).getFileName().toString(), "currency_rates.txt");
	}

	@Test
	public void givenFileNameInResourcesFolderRetrunInstanceOfCurrencyRateCSVReaderWithSameFilePath() throws Exception {
		CurrencyRatesCSVReaderFilePathInjector csvFileInjector = new CurrencyRatesCSVReaderFilePathInjector();

		String resourcesPath = new File("src/main/resources").getAbsolutePath();

		final Path myFilePath = Paths.get(resourcesPath).resolve("myfile.txt");
		if (!Files.exists(myFilePath)) {
			Files.createFile(myFilePath);
		}

		CurrencyRatesCSVReader currencyRatesCSVReader = csvFileInjector.getCurrencyRatesCsvReaderInstance("myfile.txt");

		assertEquals(Paths.get(currencyRatesCSVReader.getFilePath()), myFilePath);
	}

}
