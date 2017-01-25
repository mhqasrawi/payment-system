package com.progressoft.jip.currency.currenciesprovider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author u623
 */
public class CurrencyProviderImpl implements CurrencyProvider {

    private static Logger logger = LoggerFactory.getLogger(CurrencyProviderImpl.class);

    private List<String> currencyList = new ArrayList<>();
    private String path;

    public CurrencyProviderImpl(String path) {
        this.path = path;
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
            logger.error(e.getMessage(), e);
            throw new CurrencyException("file not found", e);
        }
        Collections.sort(currencyList);
        return currencyList;

    }
}
