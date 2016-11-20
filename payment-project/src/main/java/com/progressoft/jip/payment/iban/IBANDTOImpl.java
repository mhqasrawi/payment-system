package com.progressoft.jip.payment.iban;

public class IBANDTOImpl implements IBANDTO {

	private long id;
	private String countryCode;
	private String ibanValue;
	

	
	public IBANDTOImpl(){}
	public void setId(long id) {
		this.id = id;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public void setIbanValue(String ibanValue) {
		this.ibanValue = ibanValue;
	}

	public long getId() {
		return this.id;
	}

	public String getCountryCode() {
		return this.countryCode;
	}

	public String getIBANValue() {
		return this.ibanValue;
	}

}
