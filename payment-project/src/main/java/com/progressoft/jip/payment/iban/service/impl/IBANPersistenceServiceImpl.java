package com.progressoft.jip.payment.iban.service.impl;

import com.progressoft.jip.payment.iban.IBANDTO;
import com.progressoft.jip.payment.iban.dao.IBANDAO;
import com.progressoft.jip.payment.iban.service.IBANPersistenceService;

public class IBANPersistenceServiceImpl implements IBANPersistenceService {

    private IBANDAO ibandao;

    public IBANPersistenceServiceImpl(IBANDAO ibandao) {

    }

    public IBANDTO getIBAN(String iban) {
	return null;
    }

    public IBANDTO getIBANById(String id) {
	return null;
    }

    public IBANDTO save(IBANDTO ibanDTO) {
	return null;
    }

    public Iterable<IBANDTO> getAll() {
	return null;
    }

}
