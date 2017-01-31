package com.progressoft.jip.payment.validation.rules.norestriction;

import com.progressoft.jip.payment.PaymentInfo;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;

public class NoRestrictionRulesTest {
    private static final String NO_RESTRICATION_ON_DATE = "No Restrication On Date";
    private static final String NO_RESTRICTION_RULES_ID = "no-restriction-rules";

    private NoRestrictionRules noRestrictionRules;
    private PaymentInfo paymentInfo;

    @Before
    public void setup() {
        noRestrictionRules = new NoRestrictionRules();
        paymentInfo = Mockito.mock(PaymentInfo.class);
    }

    @Test(expected = NoRestrictionRulesException.class)
    public void WhenCallingNoRestrictionRulesWithNullArgument_ShouldThrowExcepetion() {
        noRestrictionRules.validatePaymentDate(null);
    }

    @Test
    public void WhenCallingGetIdNoRestrictionRules_ThenShouldReturnHisId() {
        assertEquals(NO_RESTRICTION_RULES_ID, noRestrictionRules.getId());
    }

    @Test
    public void WhenCallingGetDescriprtionNoRestrictionRules_ThenShouldReturnHisDescription() {
        assertEquals(NO_RESTRICATION_ON_DATE, noRestrictionRules.getDescription());
    }

    @Test(expected = NoRestrictionRulesException.class)
    public void WhenCallingValidateWithoutPaymentDate_ShouldThrowExecption() {
        noRestrictionRules.validatePaymentDate(paymentInfo);
    }

    @Test
    public void WhenCallingValidateWithValidPayment_ShouldReturnTrue() {
        Mockito.when(paymentInfo.getPaymentDate()).thenReturn(LocalDateTime.now());
        noRestrictionRules.validatePaymentDate(paymentInfo);
    }


}
