package com.progressoft.jip.ui.field;

import com.progressoft.jip.payment.iban.IBANDTO;
import com.progressoft.jip.payment.iban.IBANValidator;

/**
 * @author u612
 */
public class IBANField extends AbstractField<IBANDTO> {

    private IBANValidator ibanValidator;

    public IBANField(IBANValidator ibanValidator) {
        this.ibanValidator = ibanValidator;
    }

    @Override
    public AbstractField<IBANDTO> setValue(String value) {
        IBANDTOImpl iban = new IBANDTOImpl();
        iban.setIBANValue(value);
        ibanValidator.validate(iban);
        this.value = iban;
        return this;
    }

    private class IBANDTOImpl implements IBANDTO {

        private String ibanValue;

        @Override
        public int getId() {
            return 1;
        }

        @Override
        public String getCountryCode() {
            return ibanValue.substring(0, 2);
        }

        @Override
        public String getIBANValue() {
            return ibanValue;
        }

        public void setIBANValue(String iban) {
            this.ibanValue = iban;
        }

    }
}
