package com.progressoft.jip.ui.field;

import java.math.BigDecimal;

public class BigDecimalField extends AbstractField<BigDecimal> {

    @Override
    public AbstractField<BigDecimal> setValue(String value) {
        this.value = new BigDecimal(value);
        return this;
    }

}
