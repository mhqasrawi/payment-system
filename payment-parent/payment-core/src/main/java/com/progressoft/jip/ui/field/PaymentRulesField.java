package com.progressoft.jip.ui.field;

import com.progressoft.jip.DateRulesValidationProvider;
import com.progressoft.jip.PaymentMenuContext;
import com.progressoft.jip.actions.forms.NewAccountForm;

import java.util.HashMap;
import java.util.Map;

public class PaymentRulesField extends AbstractMultipleChoiceField<String> implements FieldThatNeedMenuContext<PaymentMenuContext> {

    private Map<String, String> dateRules = new HashMap<>();
    private PaymentMenuContext menuContext;

    public PaymentRulesField(DateRulesValidationProvider dateRulesValidationProvider) {
        dateRulesValidationProvider.getDateRules().iterator()
                .forEachRemaining(dateRule -> dateRules.put(dateRule.getDescription(), dateRule.getId()));
        dateRulesValidationProvider.getDateRules().iterator()
                .forEachRemaining(dateRule -> addChoice(dateRule.getDescription()));
    }

    @Override
    public void setMenuContext(PaymentMenuContext menuContext) {
        this.menuContext = menuContext;
    }

    @Override
    public AbstractField<String> setValue(String value) {
        if ("xDays".equals(dateRules.get(value))) {
            menuContext.put(NewAccountForm.IS_THERE_EXTRA_INFO, Boolean.valueOf(true));
        } else {
            menuContext.put(NewAccountForm.IS_THERE_EXTRA_INFO, Boolean.valueOf(false));
        }
        this.value = dateRules.get(value);
        return this;
    }

}
