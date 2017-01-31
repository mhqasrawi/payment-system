package com.progressoft.jip.payment.validation.rules;

import com.progressoft.jip.DateRulesValidationProvider;
import com.progressoft.jip.IdDescreptionPair;

public class DateRulesValidationProviderImpl implements DateRulesValidationProvider {

    @Override
    public Iterable<IdDescreptionPair> getDateRules() {
        return DateValidationRules.getDateValidationRulesDescription();
    }

}
