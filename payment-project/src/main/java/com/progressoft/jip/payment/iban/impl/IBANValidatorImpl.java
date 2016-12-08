package com.progressoft.jip.payment.iban.impl;

import java.io.IOException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;

import com.progressoft.jip.payment.iban.IBANDTO;
import com.progressoft.jip.payment.iban.IBANValidationException;
import com.progressoft.jip.payment.iban.IBANValidator;
import com.progressoft.jip.payment.iban.impl.IBANPattern.IBANPatternType;
import com.progressoft.jip.payment.iban.impl.IBANPattern.IBANSequencePair;

public class IBANValidatorImpl implements IBANValidator{
	
	private final String NUMERIC_REGEX = "[0-9]";
	private final String ALPHABETIC_REGEX = "[A-Z]";
	private final String ALPHANUMERIC_REGEX = "[0-9A-Z]";
	private HashMap<String, String> regexCache = new HashMap<>();
	private HashMap<String, IBANPattern> patternCache = new HashMap<>();

	private final IBANFormatsReader reader;
	private IBANPattern correctPattern;
	private IBANDTO iban;

	@Autowired
	public IBANValidatorImpl(IBANFormatsReader reader) {
		this.reader = reader;
	}

	public void validate(IBANDTO iban) {
		this.iban = iban;
		try {
			if(!checkFormat() || getRemainder() != 1)
				throw new IBANValidationException("IBAN is invalid");
		} catch (IOException e) {
			throw new RuntimeException(e);
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
		IBANPattern next = null;
		while (iterator.hasNext()) {
			next = iterator.next();
			String countryCode = next.getCountryCode();
			patternCache.put(countryCode, next);
			regexCache.put(countryCode, buildPatternRegex(next));
		}
	}

	private boolean checkFormat() throws IOException {
		findIBANStructure();
		if (correctPattern == null || iban.getIBANValue().length() != correctPattern.getIbanLength() || !checkRegex())
			return false;
		return true;
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
		BigInteger bigInt = new BigInteger(IBANNumericValue());
		BigInteger[] divAndRem = bigInt.divideAndRemainder(BigInteger.valueOf(97));
		int mod = divAndRem[1].intValue();
		return mod;
	}

	private String IBANNumericValue() {
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