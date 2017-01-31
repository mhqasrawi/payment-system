package com.progressoft.jip.tests.currencyrate;

import com.progressoft.jip.currency.rate.provider.impl.CurrencyRatesCSVReader;
import org.junit.Test;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CurrenyRatesCSVReaderTest {

    private static final String MYFILE_CSV = "./myfile.csv";

    @Test
    public void givenFilePathCreateNewCurrencyRatesCSVReader() throws Exception {
        CurrencyRatesCSVReader currencyRatesCSVReader = new CurrencyRatesCSVReader(MYFILE_CSV);
        assertNotNull(currencyRatesCSVReader);
    }

    @Test
    public void givenFilePathCreateNewCurrencyRatesCSVReaderAndGetFilePath() throws Exception {
        String filePath = new String();

        CurrencyRatesCSVReader currenyRatesCSVReader = new CurrencyRatesCSVReader(filePath);
        assertEquals(currenyRatesCSVReader.getFilePath(), filePath);
    }

    @Test
    public void givenFilePathAndCurrencyGetRate() throws Exception {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(MYFILE_CSV))) {
            bufferedWriter.write("JOD,1.4");
        } catch (IOException e) {
            e.printStackTrace();
        }

        CurrencyRatesCSVReader currencyRatesCSVReader = new CurrencyRatesCSVReader(MYFILE_CSV);
        assertEquals(currencyRatesCSVReader.readRateFromCurrency("JOD"), BigDecimal.valueOf(1.4));
    }

    @Test
    public void givenFilePathAndCurrencyNotInFileReturnNull() throws Exception {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(MYFILE_CSV))) {
            bufferedWriter.write("hello,44\r\n");
        } catch (IOException e) {
            e.printStackTrace();
        }

        CurrencyRatesCSVReader currencyRatesCSVReader = new CurrencyRatesCSVReader(MYFILE_CSV);
        assertEquals(currencyRatesCSVReader.readRateFromCurrency("asdf"), null);
    }

}
