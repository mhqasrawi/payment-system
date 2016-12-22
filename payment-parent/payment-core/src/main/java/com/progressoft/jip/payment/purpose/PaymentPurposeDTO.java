package com.progressoft.jip.payment.purpose;

import com.progressoft.jip.payment.iban.DTO;

public interface PaymentPurposeDTO extends DTO {

	String getShortCode();

	String getDescription();

}
