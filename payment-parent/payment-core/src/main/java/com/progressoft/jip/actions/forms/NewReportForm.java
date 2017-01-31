package com.progressoft.jip.actions.forms;

import com.progressoft.jip.PaymentMenuContext;
import com.progressoft.jip.actions.impl.ReportSettingWrapper;
import com.progressoft.jip.payment.PaymentDAO;
import com.progressoft.jip.payment.report.core.ReportManager;
import com.progressoft.jip.payment.usecase.NewReportUseCase;
import com.progressoft.jip.ui.dynamic.menu.SubmitAction;
import com.progressoft.jip.ui.field.PathField;
import com.progressoft.jip.ui.field.StringField;
import com.progressoft.jip.ui.form.FormImpl;

import javax.inject.Inject;

public class NewReportForm extends FormImpl<PaymentMenuContext, ReportSettingWrapper>
        implements SubmitAction<PaymentMenuContext, ReportSettingWrapper> {

    private static final String FILE_NAME = "fileName";
    private static final String PATH = "path";
    private static final String ENTER_REPORT_OUTPUT_DIRECTORY = "Enter Report Output Directory";
    private static final String ENTER_REPORT_FILE_NAME = "Enter Report File Name";
    private static final String ENTER_REPORT_INFO = "Enter Report Info";

    @Inject
    private ReportManager reportManager;
    @Inject
    private PaymentDAO paymentDAO;

    public NewReportForm() {
        super(ENTER_REPORT_INFO);
    }

    public void init() {
        this.addField(new StringField().setDescription(ENTER_REPORT_FILE_NAME).setName(FILE_NAME));
        this.addField(new PathField().setDescription(ENTER_REPORT_OUTPUT_DIRECTORY).setName(PATH));

    }

    @Override
    public void submitAction(PaymentMenuContext menuContext, ReportSettingWrapper reportSetting) {
        new NewReportUseCase(reportManager, paymentDAO).process(menuContext, reportSetting);
    }

    @Override
    public Class<ReportSettingWrapper> getClassType() {
        return ReportSettingWrapper.class;
    }

}
