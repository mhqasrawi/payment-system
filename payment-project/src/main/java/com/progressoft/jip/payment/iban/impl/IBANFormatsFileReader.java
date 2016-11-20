/**
 * 
 */
package com.progressoft.jip.payment.iban.impl;

import com.progressoft.jip.payment.iban.impl.IBANPattern.IBANPatternType;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

/**
 * @author u620
 *
 */
public class IBANFormatsFileReader implements IBANFormatsReader {

	private String ibanFormatsFilePath;
	private ArrayList<IBANPattern> patterns = new ArrayList<>();
	private static HashMap<Character, IBANPatternType> patternMapper = new HashMap<>();

	static {
			patternMapper.put('a', IBANPatternType.ALPHABETIC);
			patternMapper.put('n', IBANPatternType.NUMERIC);
			patternMapper.put('c', IBANPatternType.ALPHANUMERIC);
	}
	
	public IBANFormatsFileReader(String ibanFormatsFilePath) {
		this.ibanFormatsFilePath = ibanFormatsFilePath;
	}

	private void loadAllIBANStructures() throws FileNotFoundException, IOException {
		String[] tokens;
		String field;
		String countryCode;
		String pattern;
		int length;
		try (BufferedReader reader = new BufferedReader(new FileReader(ibanFormatsFilePath))) {
			while ((field = reader.readLine()) != null) {
				tokens = field.split(",");
				countryCode = tokens[0];
				pattern = tokens[1];
				length = Integer.parseInt(tokens[2]);
				addPattern(countryCode, pattern, length);
			}
		}
	}

	private void addPattern(String countryCode, String iBANStructure, int ibanLength) {
		String[] iBANPatternTokens = iBANStructure.split("!");
		String[] patternTypes = new String[iBANPatternTokens.length];
		int[] patternLength = new int[iBANPatternTokens.length];
		IBANPattern pattern = new IBANPattern();
		pattern.setCountryCode(countryCode);
		pattern.setIbanLength(ibanLength);
		for (int i = 0; i < patternLength.length; i++) {
			patternTypes[i] = iBANPatternTokens[i].substring(0, 1);
			patternLength[i] = Integer.parseInt(iBANPatternTokens[i].substring(1));
			pattern.addSubset(patternMapper.get(patternTypes[i]), patternLength[i]);
		}
		patterns.add(pattern);
	}

	public Iterator<IBANPattern> readAll() throws IOException {
		try {
			loadAllIBANStructures();
		} catch (IOException e) {
			throw new IOException("A problem occured while reading from the iban formats file: " + e.getMessage());
		}
		return Collections.unmodifiableList(patterns).iterator();
	}
}
