package com.progressoft.jip.payment.transcription;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Currency;

public class TranscriptionTest {

    private Transpict englishTranscription;

    @Before
    public void setUp() {
        englishTranscription = TranspictionFactory.newInstance(TranspictionFactory.LANGUAGE.ENG, Currency.getInstance("USD"));
    }

    private void testEnglishTranscription(String expected, long number) {
        Assert.assertEquals(expected, englishTranscription.transcript(number));
    }

    @Test
    public void GivenNumber_WhenTranspict_ThenNumberInWordsFormatReturned() {
        testEnglishTranscription("one USD", 1);
        testEnglishTranscription("eleven USD", 11);
        testEnglishTranscription("twelve USD", 12);
        testEnglishTranscription("one thousand one USD", 1001);
        testEnglishTranscription("one thousand USD", 1000);
        testEnglishTranscription("three thousand three hundred twenty five USD", 3325);
        testEnglishTranscription("nine thousand seven hundred thirty six USD", 9736);
        testEnglishTranscription("one million three hundred fifty thousand nine hundred seventy two USD", 1350972);
        testEnglishTranscription("one hundred seventy two thousand six hundred forty USD", 172640);
    }

    @Test(expected = PaymentAmountException.class)
    public void GivenNegativeAmount_WhenTransScript_ThenPaymentAmountExceptionThrown() {
        englishTranscription.transcript(-10);
    }

}
