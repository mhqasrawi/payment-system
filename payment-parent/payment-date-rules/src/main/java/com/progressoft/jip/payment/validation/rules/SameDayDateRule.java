package com.progressoft.jip.payment.validation.rules;

import com.progressoft.jip.payment.PaymentInfo;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class SameDayDateRule implements DateRule {

    private static final int ONE_DAY = 1;

    @Override
    public boolean validatePaymentDate(PaymentInfo paymentInfo) {
        if (isPastPaymentDate(paymentInfo))
            return false;
        if (isPaymentDateWithinOneMonth(paymentInfo))
            return true;
        return false;
    }

    private boolean isPastPaymentDate(PaymentInfo paymentInfo) {
        return Duration.between(
                LocalDateTime.of(LocalDate.now(), LocalTime.of(LocalTime.now().getHour(), LocalTime.now().getMinute())),
                paymentInfo.getSettlementDate()).isNegative();
    }

    private boolean isPaymentDateWithinOneMonth(PaymentInfo paymentInfo) {
        return Duration.between(LocalDateTime.now(), paymentInfo.getSettlementDate())
                .compareTo(Duration.ofDays(ONE_DAY)) <= 0;
    }

    @Override
    public String getId() {
        return "same-day";
    }

    @Override
    public String getDescription() {
        return "Payment should be within the same day";
    }

}
