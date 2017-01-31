package com.progressoft.jip.payment.transcription;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class EnglishTranscriptionTest {

    public static final String ZERO = "zero";
    public static final String FIFTY_FIVE_USD_AND_FIVE_CENTS = "fifty five USD and five cents";
    public static final String ZERO_USD_AND_SIXTY_THREE_CENTS = "zero USD and sixty three cents";
    private static final String ONE_HUNDRED_SEVENTY_TWO_THOUSAND_SIX_HUNDRED_FORTY_USD = "one hundred seventy two thousand six hundred forty USD and zero cents";
    private static final String ONE_MILLION_THREE_HUNDRED_FIFTY_THOUSAND_NINE_HUNDRED_SEVENTY_TWO_USD = "one million three hundred fifty thousand nine hundred seventy two USD and zero cents";
    private static final String NINE_THOUSAND_SEVEN_HUNDRED_THIRTY_SIX_USD = "nine thousand seven hundred thirty six USD and zero cents";
    private static final String THREE_THOUSAND_THREE_HUNDRED_TWENTY_FIVE_USD = "three thousand three hundred twenty five USD and zero cents";
    private static final String ONE_THOUSAND_USD = "one thousand USD and zero cents";
    private static final String ONE_THOUSAND_ONE_USD = "one thousand one USD and zero cents";
    private static final String TWELVE_USD = "twelve USD and zero cents";
    private static final String ELEVEN_USD = "eleven USD and zero cents";
    private static final String ONE_USD = "one USD and zero cents";
    private Transcription transcription;

    @Before
    public void setUp() {
        transcription = TranspictionFactory.newInstance("ENG");
    }

    private void testEnglishTranscription(String expected, float number) {
        Assert.assertEquals(expected, transcription.transcript(number));
    }

    @Test
    public void GivenNumber_WhenTranspict_ThenNumberInWordsFormatReturned() {
        testEnglishTranscription(ONE_USD, 1);
        testEnglishTranscription(ELEVEN_USD, 11);
        testEnglishTranscription(TWELVE_USD, 12);
        testEnglishTranscription(ONE_THOUSAND_ONE_USD, 1001);
        testEnglishTranscription(ONE_THOUSAND_USD, 1000);
        testEnglishTranscription(THREE_THOUSAND_THREE_HUNDRED_TWENTY_FIVE_USD, 3325);
        testEnglishTranscription(NINE_THOUSAND_SEVEN_HUNDRED_THIRTY_SIX_USD, 9736);
        testEnglishTranscription(ONE_MILLION_THREE_HUNDRED_FIFTY_THOUSAND_NINE_HUNDRED_SEVENTY_TWO_USD, 1350972);
        testEnglishTranscription(ONE_HUNDRED_SEVENTY_TWO_THOUSAND_SIX_HUNDRED_FORTY_USD, 172640);
        testEnglishTranscription(ZERO, 0);
        testEnglishTranscription(FIFTY_FIVE_USD_AND_FIVE_CENTS, 55.5f);
        testEnglishTranscription(ZERO, 0.0f);
        testEnglishTranscription(ZERO_USD_AND_SIXTY_THREE_CENTS, 0.63f);
    }

    @Test(expected = PaymentAmountException.class)
    public void GivenNegativeAmount_WhenTransScript_ThenPaymentAmountExceptionThrown() {
        transcription.transcript(-10);
    }
}
