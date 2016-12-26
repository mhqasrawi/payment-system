package com.progressoft.jip.payment.validation.rules;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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
		String paymentRuleId = info.getOrderingAccount().getPaymentRule();
		DateRule dateRule = dateRulesValidation.get(paymentRuleId);
		if(Objects.isNull(dateRule)){
			throw new DateValidationRulesException("No Date Validation Rules For These Rules "+paymentRuleId);
		}
		dateRule.validatePaymentDate(info);
	}

	public static Iterable<IdDescreptionPair> getDateValidationRulesDescription() {
		return idDescreptionPairs;
	}
	
	public class DateValidationRulesException extends RuntimeException{
		
		private static final long serialVersionUID = 6434763956175433043L;

		public DateValidationRulesException(String message){
			super(message);
		}
	}

}
