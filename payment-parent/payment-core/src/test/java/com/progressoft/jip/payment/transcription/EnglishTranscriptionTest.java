package com.progressoft.jip.payment.transcription;

import java.util.Currency;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class EnglishTranscriptionTest {

	private static final String ONE_HUNDRED_SEVENTY_TWO_THOUSAND_SIX_HUNDRED_FORTY_USD = "one hundred seventy two thousand six hundred forty USD";
	private static final String ONE_MILLION_THREE_HUNDRED_FIFTY_THOUSAND_NINE_HUNDRED_SEVENTY_TWO_USD = "one million three hundred fifty thousand nine hundred seventy two USD";
	private static final String NINE_THOUSAND_SEVEN_HUNDRED_THIRTY_SIX_USD = "nine thousand seven hundred thirty six USD";
	private static final String THREE_THOUSAND_THREE_HUNDRED_TWENTY_FIVE_USD = "three thousand three hundred twenty five USD";
	private static final String ONE_THOUSAND_USD = "one thousand USD";
	private static final String ONE_THOUSAND_ONE_USD = "one thousand one USD";
	private static final String TWELVE_USD = "twelve USD";
	private static final String ELEVEN_USD = "eleven USD";
	private static final String ONE_USD = "one USD";
	private Transcription transcription;

	@Before
	public void setUp() {
		transcription = TranspictionFactory.newInstance("ENG", Currency.getInstance("USD"));
	}

	private void testEnglishTranscription(String expected, long number) {
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
	}

	@Test(expected = PaymentAmountException.class)
	public void GivenNegativeAmount_WhenTransScript_ThenPaymentAmountExceptionThrown() {
		transcription.transcript(-10);
	}
}
