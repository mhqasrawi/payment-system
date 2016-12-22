package com.progressoft.jip.payment.purpose;

public class PaymentPurposeDTOImpl implements PaymentPurposeDTO {

	private int id;
	private String shortCode;

	private String description;

	@Override
	public int getId() {
		return this.id;
	}

	@Override
	public String getShortCode() {
		return this.shortCode;
	}

	@Override
	public String getDescription() {
		return this.description;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setShortCode(String shortCode) {
		this.shortCode = shortCode;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}