package com.progressoft.jip.payment.validation.rules;

import com.progressoft.jip.payment.PaymentInfo;

import java.time.Duration;
import java.time.LocalDateTime;

public class CustomBoundDateRule implements DateRule {

    private static final String DESCRIPTION = "Allow payments only within a specified number of days";

    @Override
    public boolean validatePaymentDate(PaymentInfo paymentInfo) {
        validatePaymentInfo(paymentInfo);
        String paymentRuleInfo = paymentInfo.getOrderingAccount().getPaymentRuleInfo();
        long days = -1;
        try {
            days = Integer.parseInt(paymentRuleInfo);
        } catch (NumberFormatException e) {
            throwException("Invalid input in payment info; paymentRuleInfo: " + paymentRuleInfo);
        }
        if (days < 0)
            throwException("Days cannot be negative");
        LocalDateTime paymentDate = paymentInfo.getSettlementDate();
        Duration period = Duration.between(LocalDateTime.now(), paymentDate);
        return period.minusDays(days).isNegative() || period.minusDays(days).isZero();
    }

    private void validatePaymentInfo(PaymentInfo paymentInfo) {
        if (paymentInfo == null) {
            throwException("Payment Info cannot be null");
        }
        if (paymentInfo.getOrderingAccount() == null) {
            throwException("Ordering Account cannot be null");
        }
        if (paymentInfo.getOrderingAccount().getPaymentRuleInfo() == null
                || paymentInfo.getOrderingAccount().getPaymentRuleInfo().isEmpty()) {
            throwException("Payment rule info cannot be null or empty for a Custom Bound Date Rule");
        }
        if (!Duration.between(paymentInfo.getSettlementDate(), LocalDateTime.now()).minus(Duration.ofSeconds(3))
                .isNegative()) {
            throwException("Invalid payment date: " + paymentInfo.getSettlementDate());
        }
    }

    private void throwException(String message) {
        throw new DateRuleException(message);
    }

    @Override
    public String getId() {
        return "xDays";
    }

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }
}