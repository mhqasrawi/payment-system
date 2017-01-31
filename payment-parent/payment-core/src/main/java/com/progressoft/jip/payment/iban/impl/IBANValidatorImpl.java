package com.progressoft.jip.payment.iban.impl;

import com.progressoft.jip.payment.iban.IBANDTO;
import com.progressoft.jip.payment.iban.IBANValidationException;
import com.progressoft.jip.payment.iban.IBANValidator;
import com.progressoft.jip.payment.iban.impl.IBANPattern.IBANPatternType;
import com.progressoft.jip.payment.iban.impl.IBANPattern.IBANSequencePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.io.IOException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Iterator;

public class IBANValidatorImpl implements IBANValidator {
    private static final Logger LOGGER = LoggerFactory.getLogger(IBANValidatorImpl.class);
    private static final String NUMERIC_REGEX = "[0-9]";
    private static final String ALPHABETIC_REGEX = "[A-Z]";
    private static final String ALPHANUMERIC_REGEX = "[0-9A-Z]";
    private final IBANFormatsReader reader;
    private HashMap<String, String> regexCache = new HashMap<>();
    private HashMap<String, IBANPattern> patternCache = new HashMap<>();
    private IBANPattern correctPattern;
    private IBANDTO iban;

    @Inject
    public IBANValidatorImpl(IBANFormatsReader reader) {
        this.reader = reader;
    }

    @Override
    public void validate(IBANDTO iban) {
        this.iban = iban;
        try {
            if (!checkFormat()) {
                throw new IBANValidationException("IBAN format is invalid");
            }
            if (getRemainder() != 1) {
                throw new IBANValidationException("IBAN checksum is invalid");
            }
        } catch (IOException e) {
            LOGGER.error("Failed while validating IBAN", e);
            throw new IBANValidationException("Failed while reading IBAN formats file", e);
        }
    }

    public void findIBANStructure() throws IOException {
        if (patternCache.isEmpty()) {
            populateCaches();
        }
        this.correctPattern = patternCache.get(iban.getCountryCode());
    }

    private void populateCaches() throws IOException {
        Iterator<IBANPattern> iterator = reader.readAll();
        IBANPattern next;
        while (iterator.hasNext()) {
            next = iterator.next();
            String countryCode = next.getCountryCode();
            patternCache.put(countryCode, next);
            regexCache.put(countryCode, buildPatternRegex(next));
        }
    }

    private boolean checkFormat() throws IOException {
        findIBANStructure();
        return !(correctPattern == null || iban.getIBANValue().length() != correctPattern.getIbanLength()
                || !checkRegex());
    }

    private boolean checkRegex() {
        return iban.getIBANValue().substring(2).matches(regexCache.get(iban.getCountryCode()));
    }

    private String buildPatternRegex(IBANPattern pattern) {
        StringBuilder regex = new StringBuilder("");
        Iterator<IBANSequencePair> iterator = pattern.getPatterns();
        while (iterator.hasNext()) {
            IBANSequencePair set = iterator.next();
            if (set.getType() == IBANPatternType.NUMERIC)
                regex.append(NUMERIC_REGEX);
            else if (set.getType() == IBANPatternType.ALPHABETIC)
                regex.append(ALPHABETIC_REGEX);
            else
                regex.append(ALPHANUMERIC_REGEX);
            regex.append("{").append(set.getLength()).append("}");
        }
        return regex.toString();
    }

    private int getRemainder() {
        BigInteger bigInt = new BigInteger(iBANNumericValue());
        BigInteger[] divAndRem = bigInt.divideAndRemainder(BigInteger.valueOf(97));
        return divAndRem[1].intValue();
    }

    private String iBANNumericValue() {
        StringBuilder formattedIBAN = new StringBuilder("");
        char[] ibanArray;
        int numericValue;
        ibanArray = moveCountryCodeToTail().toCharArray();
        for (int i = 0; i < ibanArray.length; i++) {
            numericValue = Character.getNumericValue(ibanArray[i]);
            if (isLetter(numericValue))
                formattedIBAN.append(String.valueOf(numericValue));
            else
                formattedIBAN.append(ibanArray[i]);
        }
        return formattedIBAN.toString();
    }

    private boolean isLetter(int numericValue) {
        return numericValue >= 10 && numericValue <= 35;
    }

    private String moveCountryCodeToTail() {
        return iban.getIBANValue().substring(4).concat(iban.getIBANValue().substring(0, 4));
    }
}