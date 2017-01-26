package com.progressoft.jip.payment.iban.service.impl;

import com.progressoft.jip.payment.iban.IBANDTO;
import com.progressoft.jip.payment.iban.dao.IBANDAO;
import com.progressoft.jip.payment.iban.service.IBANPersistenceService;

import javax.inject.Inject;

/**
 *
 */
public class IBANPersistenceServiceImpl implements IBANPersistenceService {

    private IBANDAO ibandao;

    @Inject
    public IBANPersistenceServiceImpl(IBANDAO ibandao) {
        this.ibandao = ibandao;

    }

    public IBANDTO getIBAN(String iban) {
        return ibandao.get(iban);
    }

    public IBANDTO getIBANById(int id) {
        return ibandao.getById(id);
    }

    public IBANDTO save(IBANDTO ibanDTO) {
        return ibandao.save(ibanDTO);
    }

    public Iterable<IBANDTO> getAll() {
        return ibandao.getAll();
    }

}
