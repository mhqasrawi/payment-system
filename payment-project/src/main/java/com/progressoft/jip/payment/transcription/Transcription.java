package com.progressoft.jip.payment.transcription;

public interface Transcription {

	String getSupportedLanguage();

	String transcript(long number);

}