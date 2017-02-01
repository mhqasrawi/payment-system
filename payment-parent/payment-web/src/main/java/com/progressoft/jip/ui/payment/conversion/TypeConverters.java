package com.progressoft.jip.ui.payment.conversion;

import com.progressoft.jip.dependency.ImplementationProvider;
import com.progressoft.jip.payment.PaymentDTO.PaymentStatus;
import com.progressoft.jip.payment.iban.IBANDTO;
import com.progressoft.jip.payment.iban.IBANDTOImpl;
import com.progressoft.jip.payment.purpose.PaymentPurposeDTO;
import com.progressoft.jip.payment.purpose.service.PaymentPurposePersistenceService;
import com.progressoft.jip.payment.usecase.LoadAllPaymentPurposeUseCase;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Currency;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by u624 on 1/26/2017.
 */
public class TypeConverters {
    private ImplementationProvider implementationProvider;

    private Converter<LocalDateTime, String> stringToLocalDateTime = s -> {
        String[] date = s.split("/");
        return LocalDateTime.of(
                LocalDate.of(Integer.parseInt(date[2]), Integer.parseInt(date[1]), Integer.parseInt(date[0])),
                LocalTime.now());
    };

    private Converter<PaymentPurposeDTO, String> stringToPaymentPurposeDTO = s -> ((List<PaymentPurposeDTO>) new LoadAllPaymentPurposeUseCase(
            implementationProvider.getImplementation(PaymentPurposePersistenceService.class)).loadPaymentPurpose()).stream().filter(p -> s.equals(p.getShortCode()))
            .findAny().orElse(null);


    private Converter<IBANDTO, String> stringToIBANDTO = s -> {
        IBANDTOImpl ibandtoImpl = new IBANDTOImpl();
        ibandtoImpl.setIbanValue(s.substring(2));
        ibandtoImpl.setCountryCode(s.substring(0, 2));
        return ibandtoImpl;
    };

    private Converter<BigDecimal, String> stringToBigDecimal = s -> BigDecimal.valueOf(Long.valueOf(s));

    private Converter<Currency, String> stringToCurrency = Currency::getInstance;

    private Map<Class<?>, Converter<?, String>> stringConverterMap = new HashMap<>();

    public TypeConverters(ImplementationProvider implementationProvider) {
        this.implementationProvider = implementationProvider;
        stringConverterMap.put(LocalDateTime.class, stringToLocalDateTime);
        stringConverterMap.put(PaymentPurposeDTO.class, stringToPaymentPurposeDTO);
        stringConverterMap.put(IBANDTO.class, stringToIBANDTO);
        stringConverterMap.put(BigDecimal.class, stringToBigDecimal);
        stringConverterMap.put(Currency.class, stringToCurrency);
    }

    public <T> T convertString(Class<T> clazz, String string) {
        return (T) stringConverterMap.get(clazz).convert(string);
    }
}
