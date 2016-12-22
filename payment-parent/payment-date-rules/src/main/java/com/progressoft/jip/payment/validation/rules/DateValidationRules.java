package com.progressoft.jip.payment.validation.rules;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;

import org.apache.commons.collections.map.HashedMap;

import com.progressoft.jip.payment.PaymentInfo;
import com.progressoft.jip.payment.impl.PaymentValidation;

/**
 *
 */
public class DateValidationRules implements PaymentValidation {

	private final static Map<String, DateRule> dateRulesValidation;
	private final static List<IdDescreptionPair> idDescreptionPairs;
	static {
		Map<String, DateRule> dateRules = new HashedMap();
		List<IdDescreptionPair> ids = new ArrayList<>();
		ServiceLoader<DateRule> serviceLoader = ServiceLoader.load(DateRule.class);
		serviceLoader.iterator().forEachRemaining((dataRule) -> {
			dateRules.put(dataRule.getId(), dataRule);
			ids.add(new IdDescreptionPair(dataRule.getId(), dataRule.getDescription()));
		});
		idDescreptionPairs = Collections.unmodifiableList(ids);
		dateRulesValidation = Collections.unmodifiableMap(dateRules);
	}

	public void validate(PaymentInfo info) {
		DateRule dateRule = dateRulesValidation.get(info.getOrderingAccount().getPaymentRule());
		dateRule.validatePaymentDate(info);
	}

	public static Iterable<IdDescreptionPair> getDateValidationRulesDescription() {
		return idDescreptionPairs;
	}

}
