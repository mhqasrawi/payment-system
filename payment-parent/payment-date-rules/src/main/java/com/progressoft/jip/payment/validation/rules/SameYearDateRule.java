package com.progressoft.jip.payment.validation.rules;

import com.progressoft.jip.payment.PaymentInfo;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class SameYearDateRule implements DateRule {

    private static final String DESCRIPTION = "Payment should be within the same year";
    private static final String ID = "same-year";
    private static final int DAYS_OF_YEAR = 365;

    @Override
    public boolean validatePaymentDate(PaymentInfo paymentInfo) {
        if (isPastPaymentDate(paymentInfo))
            return false;

        if (isPaymentWithinOneYear(paymentInfo))
            return true;

        return false;
    }

    private boolean isPastPaymentDate(PaymentInfo paymentInfo) {
        return Duration.between(
                LocalDateTime.of(LocalDate.now(), LocalTime.of(LocalTime.now().getHour(), LocalTime.now().getMinute())),
                paymentInfo.getPaymentDate()).isNegative();
    }

    private boolean isPaymentWithinOneYear(PaymentInfo paymentInfo) {
        return Duration.between(LocalDateTime.now(), paymentInfo.getPaymentDate())
                .compareTo(Duration.ofDays(DAYS_OF_YEAR)) <= 0;
    }

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }

}
