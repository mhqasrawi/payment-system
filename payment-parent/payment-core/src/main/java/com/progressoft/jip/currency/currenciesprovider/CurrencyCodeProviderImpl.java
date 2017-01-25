package com.progressoft.jip.currency.currenciesprovider;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author u623
 *
 */
public class CurrencyCodeProviderImpl implements CurrencyCodeProvider {
	private List<String> currencyList = new ArrayList<>();
	private String path = "";

	public CurrencyCodeProviderImpl() {

	}

	public CurrencyCodeProviderImpl(String path) {
		this.path = path;
	}

	public void init() {

	}

	@Override
	public Iterable<String> listAllCurrency() {

		try (FileReader fileReader = new FileReader(path);
				BufferedReader reader = new BufferedReader(fileReader)) {
			String line = "";
			while ((line = reader.readLine()) != null) {
				currencyList.add(line);
			}

		} catch (IOException e) {
			throw new CurrencyException("file not found");
		}
		Collections.sort(currencyList);
		return currencyList;

	}
}
