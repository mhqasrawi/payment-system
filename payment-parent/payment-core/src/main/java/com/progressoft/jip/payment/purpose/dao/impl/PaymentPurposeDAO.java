package com.progressoft.jip.payment.purpose.dao.impl;

import com.progressoft.jip.payment.purpose.PaymentPurposeDTO;

public interface PaymentPurposeDAO {

    PaymentPurposeDTO save(PaymentPurposeDTO paymentPurpose);

    PaymentPurposeDTO get(String shortCode);

    Iterable<PaymentPurposeDTO> getAll();

}
