package com.progressoft.jip.payment.usecase;

import com.progressoft.jip.PaymentMenuContext;
import com.progressoft.jip.actions.impl.ReportSettingWrapper;
import com.progressoft.jip.payment.PaymentDAO;
import com.progressoft.jip.payment.PaymentDTO;
import com.progressoft.jip.payment.report.core.ReportManager;
import com.progressoft.jip.payment.report.impl.ReportSettingsImpl;
import com.progressoft.jip.payment.transcription.EnglishTranscription;

public class NewReportUseCase {

    private final ReportManager reportManager;
    private final PaymentDAO paymentDAO;

    public NewReportUseCase(ReportManager reportManager, PaymentDAO paymentDAO) {
        this.reportManager = reportManager;
        this.paymentDAO = paymentDAO;
    }

    public void process(PaymentMenuContext menuContext, ReportSettingWrapper reportSetting) {
        String accountNumber = menuContext.getCurrentAccount().getAccountNumber();
        Iterable<PaymentDTO> payments = paymentDAO.get(accountNumber);
        ReportSettingsImpl settings = new ReportSettingsImpl();
        settings.setPath(reportSetting.getPath());
        settings.setFileName(reportSetting.getFileName());
        settings.setFileExtention("xml");
        settings.setPayments(payments);
        settings.setTranscriberClass(EnglishTranscription.class);
        reportManager.generateReport(settings);
    }
}
