package com.progressoft.jip.payment.validation.rules;

import com.progressoft.jip.IdDescreptionPair;
import com.progressoft.jip.payment.PaymentInfo;
import com.progressoft.jip.payment.impl.PaymentValidation;
import org.apache.commons.collections.map.HashedMap;

import java.util.*;

/**
 *
 */
public class DateValidationRules implements PaymentValidation {

    private static final Map<String, DateRule> dateRulesValidation;
    private static final List<IdDescreptionPair> idDescreptionPairs;

    static {
        Map<String, DateRule> dateRules = new HashedMap();
        List<IdDescreptionPair> ids = new ArrayList<>();
        ServiceLoader<DateRule> serviceLoader = ServiceLoader.load(DateRule.class);
        serviceLoader.iterator().forEachRemaining(dataRule -> {
            dateRules.put(dataRule.getId(), dataRule);
            ids.add(new IdDescreptionPair(dataRule.getId(), dataRule.getDescription()));
        });
        idDescreptionPairs = Collections.unmodifiableList(ids);
        dateRulesValidation = Collections.unmodifiableMap(dateRules);
    }

    public static Iterable<IdDescreptionPair> getDateValidationRulesDescription() {
        return idDescreptionPairs;
    }

    @Override
    public void validate(PaymentInfo info) {
        String paymentRuleId = info.getOrderingAccount().getPaymentRule();
        DateRule dateRule = dateRulesValidation.get(paymentRuleId);
        if (Objects.isNull(dateRule)) {
            throw new DateValidationRulesException("No Date Validation Rules For These Rules " + paymentRuleId);
        }
        dateRule.validatePaymentDate(info);
    }

    public class DateValidationRulesException extends RuntimeException {

        private static final long serialVersionUID = 6434763956175433043L;

        public DateValidationRulesException(String message) {
            super(message);
        }
    }

}
