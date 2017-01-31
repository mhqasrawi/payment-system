package com.progressoft.jip.payment.report.impl;

import com.progressoft.jip.payment.PaymentDTO;
import com.progressoft.jip.payment.report.core.ReportException;
import com.progressoft.jip.payment.report.core.ReportGenerator;
import com.progressoft.jip.payment.report.core.ReportGeneratorException;
import com.progressoft.jip.payment.report.core.ReportNode;
import com.progressoft.jip.payment.report.core.ReportNodeProvider;
import com.progressoft.jip.payment.report.core.ReportSettingsSpi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Objects;

@SuppressWarnings("rawtypes")
public abstract class AbstractReportGenerator implements ReportGenerator {
    protected static final String PAYMENT_TAG = "payment";
    protected static final String REPORT_NAME_TAG = "customer-payments-report";
//    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractReportGenerator.class);
    protected String supportedFileExtension = null;
    protected ReportSettingsSpi settingsSpi;

    @Override
    public void generateReport(ReportSettingsSpi settings) {
        this.settingsSpi = settings;
        validateSettings();
        startWrite();
        writePayments(settings.getPayments());
        endWrite();
    }

    protected abstract void startWrite();

    protected abstract void startPayment();


    protected abstract void writeNode(ReportNode node);

    protected abstract void endPayment();

    protected abstract void endWrite();

    protected void writeAndHandleException(WriteOperation writeOperation, WriteOperation onException, String message) {
        try {
            writeOperation.execute();
        } catch (ReportException e1) {
            try {
                onException.execute();
            } catch (ReportException e2) {
//                LOGGER.error("Failed while generating report", e2);
            }
            throw new ReportGeneratorException(message, e1);
        }
    }

    private void validateSettings() {
        if (isNull(settingsSpi)
                || isNull(settingsSpi.getPayments(), settingsSpi.getFileName(), settingsSpi.getPath())) {
            throw new ReportGeneratorException("Report settings fields cannot be null");
        }
        if (!settingsSpi.getPayments().iterator().hasNext() || this.settingsSpi.getFileName().isEmpty()) {
            throw new ReportGeneratorException("Invalid arguments in report settings");
        }
    }

    private boolean isNull(Object... objs) {
        for (Object obj : objs) {
            if (Objects.isNull(obj)) {
                return true;
            }
        }
        return false;
    }

	private void writePayments(Iterable<PaymentDTO> payments) {
    	ReportNodeProvider provider = this.settingsSpi.newReportNodeProviderInstance();
    	provider.setReportSettings(settingsSpi);
        for (PaymentDTO payment : payments) {
            startPayment();
            	for(ReportNode n : provider.getNodes(payment)) {
            		writeNode(n);
            	}
            endPayment();
        }
    }
}