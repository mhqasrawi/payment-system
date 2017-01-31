package com.progressoft.jip.payment.report.impl;

import com.progressoft.jip.payment.PaymentDTO;
import com.progressoft.jip.payment.report.core.ReportNodeProvider;
import com.progressoft.jip.payment.report.core.ReportSettingsException;
import com.progressoft.jip.payment.report.core.ReportSettingsSpi;
import com.progressoft.jip.payment.transcription.Transcription;
import org.slf4j.LoggerFactory;
import java.nio.file.Path;

public class ReportSettingsImpl implements ReportSettingsSpi {
    private Iterable<PaymentDTO> payments;
    private Path path;
    private String fileName;
    private String fileExtension;
    private Class<? extends Transcription> transcriberClass;
    private Class<? extends ReportNodeProvider> nodeProviderClass;

    @Override
    public Iterable<PaymentDTO> getPayments() {
        return payments;
    }

    public void setPayments(Iterable<PaymentDTO> payments) {
        this.payments = payments;
    }

    @Override
    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    @Override
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public String getFileExtention() {
        return fileExtension;
    }

    public void setFileExtention(String fileExtention) {
        this.fileExtension = fileExtention;
    }

    public void setTranscriberClass(Class<? extends Transcription> transcriberClass) {
        this.transcriberClass = transcriberClass;
    }
    
    public void setReportNodeProviderClass(Class<? extends ReportNodeProvider> clazz) {
    	this.nodeProviderClass = clazz;
    }

    @Override
    public ReportNodeProvider newReportNodeProviderInstance() {
    	return instantiate(this.nodeProviderClass);
    }
    
    @Override
    public Transcription newTranscriberInstance() {
    	return instantiate(this.transcriberClass);
    }
    
    private <T> T instantiate(Class<T> clazz) {
    	  try {
              return clazz.newInstance();
          } catch (InstantiationException | IllegalAccessException e) {
              String message = "Failed to initialize transcriberClass \"" + clazz.getSimpleName() + "\"";
              LoggerFactory.getLogger(ReportSettingsImpl.class).error(message, e);
              throw new ReportSettingsException(message, e);
          }
    }
}