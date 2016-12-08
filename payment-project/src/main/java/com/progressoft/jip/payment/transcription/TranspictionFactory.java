package com.progressoft.jip.payment.transcription;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.ServiceLoader;

/**
 * Created by mhqasrawi on 08/12/16.
 */
public class TranspictionFactory {

    private static List<Transcription> transpictions = new ArrayList<>();

    static {
        ServiceLoader<Transcription> availableTranscriptionsImpl = ServiceLoader.load(Transcription.class);
        if (availableTranscriptionsImpl != null) {
            availableTranscriptionsImpl.forEach((transcription) -> transpictions.add(transcription));
        }
    }

    private TranspictionFactory() {


    }

    public static Transcription newInstance(String lang, Currency currency) {
        for (Transcription transpiction : transpictions) {
            if(transpiction.getSupportedLanguage().equals(lang)){
                return transpiction;
            }
        }
        throw new LanguageNotSupportedException("Language Not Supporter: " + lang.toString());
    }
}
