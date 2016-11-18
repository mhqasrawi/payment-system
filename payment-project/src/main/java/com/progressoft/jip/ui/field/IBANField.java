package com.progressoft.jip;

import com.progressoft.jip.payment.iban.IBANDTO;
import com.progressoft.jip.payment.iban.IBANValidator;

public class IBANField extends AbstractField<IBANDTO> {
	private IBANValidator ibanValidator;

	public IBANField(IBANValidator ibanValidator) {
		this.ibanValidator = ibanValidator;
	}

	@Override
	public void setValue(String value) {
		IBANDTOImpl iban = new IBANDTOImpl();
		iban.setIBANValue(value);
		ibanValidator.validate(iban);
		this.value = iban;
	}

	private class IBANDTOImpl implements IBANDTO {
		private String countryCode;
		private String ibanValue;

		public void setIBANValue(String iban) {
			this.ibanValue = iban;
		}

		@Override
		public long getId() {
			return 0;
		}

		@Override
		public String getCountryCode() {
			return null;
		}

		@Override
		public String getIBANValue() {
			return ibanValue;
		}

	}
}
