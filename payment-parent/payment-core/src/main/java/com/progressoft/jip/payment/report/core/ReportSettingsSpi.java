package com.progressoft.jip.payment.report.core;

import com.progressoft.jip.payment.PaymentDTO;
import com.progressoft.jip.payment.transcription.Transcription;

import java.nio.file.Path;

public interface ReportSettingsSpi {

    Iterable<PaymentDTO> getPayments();

    Path getPath();

    String getFileName();

    String getFileExtention();

    Transcription newTranscriberInstance();

}