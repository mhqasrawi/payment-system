package com.progressoft.jip.currency.rate.provider.impl;

import com.progressoft.jip.currency.currenciesprovider.CurrencyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;

public class CurrencyRatesCSVReader {
    private static Logger logger = LoggerFactory.getLogger(CurrencyRatesCSVReader.class);

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
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new CurrencyException("file not found", e);
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
