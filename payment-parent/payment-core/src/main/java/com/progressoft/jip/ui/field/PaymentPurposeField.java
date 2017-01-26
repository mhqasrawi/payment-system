package com.progressoft.jip.ui.field;

import com.progressoft.jip.payment.purpose.PaymentPurposeDTO;
import com.progressoft.jip.payment.purpose.dao.impl.PaymentPurposeDAO;

public class PaymentPurposeField extends AbstractField<PaymentPurposeDTO> {

    private PaymentPurposeDAO paymentPurposeDao;

    public PaymentPurposeField(PaymentPurposeDAO paymentPurposeDao) {
        this.paymentPurposeDao = paymentPurposeDao;
    }

    @Override
    public AbstractField<PaymentPurposeDTO> setValue(String value) {
        this.value = paymentPurposeDao.get(value);
        return this;
    }

}
