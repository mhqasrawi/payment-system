package com.progressoft.jip.payment.transcription;

import java.text.DecimalFormat;

public class EnglishTranscription implements Transcription {

	private static final String ZERO = "zero";

	private static final String HUNDRED = " hundred";

	private static final String ONE_THOUSAND = "one thousand ";

	private static final String THOUSAND = " thousand ";

	private static final String MILLION = " million ";

	private static final String BILLION = " billion ";

	private static final String[] tensNames = { "", " ten", " twenty", " thirty", " forty", " fifty", " sixty",
			" seventy", " eighty", " ninety" };

	private static final String[] numNames = { "", " one", " two", " three", " four", " five", " six", " seven",
			" eight", " nine", " ten", " eleven", " twelve", " thirteen", " fourteen", " fifteen", " sixteen",
			" seventeen", " eighteen", " nineteen" };

	private static final String CURRENCY = " USD";

	public static final String ENG = "ENG";

	private String convertLessThanOneThousand(int number) {
		String numWords;
		int num = number;

		if (num % 100 < 20) {
			numWords = numNames[num % 100];
			num /= 100;
		} else {
			numWords = numNames[num % 10];
			num /= 10;

			numWords = tensNames[num % 10] + numWords;
			num /= 10;
		}
		if (num == 0)
			return numWords;
		return numNames[num] + HUNDRED + numWords;
	}

	@Override
	public String getSupportedLanguage() {
		return ENG;
	}

	@Override
	public String transcript(float number) {

		if (number < 0)
			throw new PaymentAmountException("Ammount Cannot Be Negative");

		if (Float.compare(number, 0f)==0) {
			return ZERO;
		}

		String snumber = Float.toString(number);

		snumber = padZeros(Integer.parseInt(snumber.substring(0, snumber.indexOf('.'))));

		int billions = extractBillions(snumber);
		int millions = extractMillions(snumber);
		int hundredThousands = extractHunderedThousands(snumber);
		int thousands = extraxtThousands(snumber);

		String result = convertBillions(billions) + convertMillions(millions)
				+ convertHundredThousands(hundredThousands) + convertThousands(thousands);

		String numberInWords;
		if("".equals(result.trim())) {
			numberInWords = ZERO + CURRENCY + " and" + getCents(number) + " cents";
		}else {
			numberInWords = removeExtraSpaces(result).trim() + CURRENCY + " and" + getCents(number) + " cents";
		}
		return numberInWords.trim();
	}

	private String getCents(float number) {
		String snumber;
		snumber = Float.toString(number);
		int pos = snumber.indexOf('.');
		String scents;
		if(pos != -1) {
			int cents = Integer.parseInt(snumber.substring(pos +1));
			if(cents != 0) {
				scents = convertThousands(cents);
			}else {
				scents = " zero";
			}
		} else {
			scents = " zero";
		}
		return scents;
	}

	private String padZeros(long number) {
		String snumber;
		String mask = "000000000000";
		DecimalFormat df = new DecimalFormat(mask);
		snumber = df.format(number);
		return snumber;
	}

	private String convertThousands(int thousands) {
		String tradThousand;
		tradThousand = convertLessThanOneThousand(thousands);
		return tradThousand;
	}

	private String convertHundredThousands(int hundredThousands) {
		String tradHundredThousands;
		switch (hundredThousands) {
		case 0:
			tradHundredThousands = "";
			break;
		case 1:
			tradHundredThousands = ONE_THOUSAND;
			break;
		default:
			tradHundredThousands = convertLessThanOneThousand(hundredThousands) + THOUSAND;
		}
		return tradHundredThousands;
	}

	private String convertMillions(int millions) {
		String tradMillions;
		switch (millions) {
		case 0:
			tradMillions = "";
			break;
		case 1:
			tradMillions = convertLessThanOneThousand(millions) + MILLION;
			break;
		default:
			tradMillions = convertLessThanOneThousand(millions) + MILLION;
		}
		return tradMillions;
	}

	private String convertBillions(int billions) {
		String tradBillions;
		switch (billions) {
		case 0:
			tradBillions = "";
			break;
		case 1:
			tradBillions = convertLessThanOneThousand(billions) + BILLION;
			break;
		default:
			tradBillions = convertLessThanOneThousand(billions) + BILLION;
		}
		return tradBillions;
	}

	private int extraxtThousands(String snumber) {
		String thousandsPart = snumber.substring(9, 12);
		return Integer.parseInt(thousandsPart);
	}

	private int extractHunderedThousands(String snumber) {
		String hunderdThousandsPart = snumber.substring(6, 9);
		return Integer.parseInt(hunderdThousandsPart);
	}

	private int extractMillions(String snumber) {
		String millionsPart = snumber.substring(3, 6);
		return Integer.parseInt(millionsPart);
	}

	private int extractBillions(String snumber) {
		String billionsPart = snumber.substring(0, 3);
		return Integer.parseInt(billionsPart);
	}

	private String removeExtraSpaces(String result) {
		return result.replaceAll("^\\s+", "").replaceAll("\\b\\s{2,}\\b", " ");
	}

}
