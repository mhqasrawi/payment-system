package com.progressoft.jip.payment.transcription;

import java.text.DecimalFormat;

public class EnglishTranscription implements Transpict {

    private String currency;

    private static final String ZERO = "zero";

    private static final String HUNDRED = " hundred";

    private static final String ONE_THOUSAND = "one thousand ";

    private static final String THOUSAND = " thousand ";

    private static final String MILLION = " million ";

    private static final String BILLION = " billion ";

    private static final String[] tensNames = {"", " ten", " twenty", " thirty", " forty", " fifty", " sixty",
            " seventy", " eighty", " ninety"};

    private static final String[] numNames = {"", " one", " two", " three", " four", " five", " six", " seven",
            " eight", " nine", " ten", " eleven", " twelve", " thirteen", " fourteen", " fifteen", " sixteen",
            " seventeen", " eighteen", " nineteen"};

    public EnglishTranscription(String currency) {
        this.currency = currency;
    }

    private String convertLessThanOneThousand(int number) {
        String numWords;

        if (number % 100 < 20) {
            numWords = numNames[number % 100];
            number /= 100;
        } else {
            numWords = numNames[number % 10];
            number /= 10;

            numWords = tensNames[number % 10] + numWords;
            number /= 10;
        }
        if (number == 0)
            return numWords;
        return numNames[number] + HUNDRED + numWords;
    }

    public String transcript(long number) {

        if (number < 0)
            throw new PaymentAmountException("Ammount Cannot Be Negative");

        if (number == 0) {
            return ZERO;
        }

        String snumber = Long.toString(number);

        snumber = padZeros(number);

        int billions = extractBillions(snumber);
        int millions = extractMillions(snumber);
        int hundredThousands = extractHunderedThousands(snumber);
        int thousands = extraxtThousands(snumber);

        String result = convertBillions(billions) + convertMillions(millions)
                + convertHundredThousands(hundredThousands) + convertThousands(thousands);

        String numberInWords = removeExtraSpaces(result).trim() + currency;
        return numberInWords.trim();
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
        int thousands = Integer.parseInt(thousandsPart);
        return thousands;
    }

    private int extractHunderedThousands(String snumber) {
        String hunderdThousandsPart = snumber.substring(6, 9);
        int hundredThousands = Integer.parseInt(hunderdThousandsPart);
        return hundredThousands;
    }

    private int extractMillions(String snumber) {
        String millionsPart = snumber.substring(3, 6);
        int millions = Integer.parseInt(millionsPart);
        return millions;
    }

    private int extractBillions(String snumber) {
        String billionsPart = snumber.substring(0, 3);
        int billions = Integer.parseInt(billionsPart);
        return billions;
    }

    private String removeExtraSpaces(String result) {
        return result.replaceAll("^\\s+", "").replaceAll("\\b\\s{2,}\\b", " ");
    }

}
