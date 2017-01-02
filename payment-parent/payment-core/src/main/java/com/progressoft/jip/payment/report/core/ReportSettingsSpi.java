package com.progressoft.jip.payment.report.core;

import java.nio.file.Path;

import com.progressoft.jip.payment.PaymentDTO;
import com.progressoft.jip.payment.transcription.Transcription;

public interface ReportSettingsSpi {

	Iterable<PaymentDTO> getPayments();

	Path getPath();

	String getFileName();

	String getFileExtention();

	Transcription newTranscriberInstance();

}