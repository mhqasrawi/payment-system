package com.progressoft.jip.payment.transcription;

import java.util.Currency;

/**
 * Created by mhqasrawi on 08/12/16.
 */
public class TranspictionFactory {
    enum LANGUAGE {
        ENG
    }

    private TranspictionFactory() {
    }

    public static Transpict newInstance(LANGUAGE lang, Currency currency) {
        if (lang == LANGUAGE.ENG)
            return new EnglishTranscription(" "+currency.getCurrencyCode());
        throw new LanguageNotSupportedException("Language Not Supporter: " + lang.toString());
    }
}
