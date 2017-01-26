package com.progressoft.jip.payment.iban.service;

import com.progressoft.jip.payment.iban.IBANDTO;

public interface IBANPersistenceService {

    IBANDTO getIBAN(String iban);

    IBANDTO getIBANById(int id);

    IBANDTO save(IBANDTO ibanDTO);

    Iterable<IBANDTO> getAll();

}
