package com.progressoft.jip.payment.iban;

public class IBANDTOImpl implements IBANDTO {

    private int id;
    private String countryCode;
    private String ibanValue;

    public IBANDTOImpl() {

    }

    public void setIbanValue(String ibanValue) {
        this.ibanValue = ibanValue.trim().replace(" ", "");
        this.countryCode = this.ibanValue.substring(0, 2);
        this.ibanValue = ibanValue;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCountryCode() {
        return this.countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getIBANValue() {
        return this.ibanValue;
    }

}
