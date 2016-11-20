package com.progressoft.jip.payment.iban.impl;

import com.progressoft.jip.payment.iban.IBANDTO;
import com.progressoft.jip.payment.iban.IBANValidationException;

public class Test {

	public static void main(String[] args) throws IBANValidationException {
		IBANDTO iban = new IBANDTOImpl((long) 1, "AL47 2121 1009 0000 0002 3569 8741");
		IBANFormatsReader reader = new IBANFormatsFileReader("C:/Users/u612/Desktop/Temp/IBAN_Formats.txt");
		IBANValidatorImpl validator = new IBANValidatorImpl(reader);
		validator.validate(iban);
		System.out.println("IBAN is valid");
	}
	
	public static class IBANDTOImpl implements IBANDTO {

		private long id;
		private String iban;

		public IBANDTOImpl(Long id, String iban) {
			this.iban = upperCaseNoSpaces(iban);
			this.id = id;
		}

		public long getId() {
			return id;
		}

		@Override
		public String getIBANValue() {
			return iban;
		}

		@Override
		public String getCountryCode() {
			return iban.substring(0, 2);
		}

		private String upperCaseNoSpaces(String ibanArg) {
			return ibanArg.toUpperCase().replaceAll(" ", "");
		}

	}

}
