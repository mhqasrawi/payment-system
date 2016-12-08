package com.progressoft.jip.payment.iban.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.progressoft.jip.payment.iban.IBANDTO;
import com.progressoft.jip.payment.iban.dao.IBANDAO;
import com.progressoft.jip.payment.iban.service.IBANPersistenceService;

/**
 *
 */
public class IBANPersistenceServiceImpl implements IBANPersistenceService {

	private IBANDAO ibandao;

	@Autowired
	public IBANPersistenceServiceImpl(IBANDAO ibandao) {
		this.ibandao = ibandao;

	}

	public IBANDTO getIBAN(String iban) {
		return ibandao.get(iban);
	}

	public IBANDTO getIBANById(long id) {
		return ibandao.getById(id);
	}

	public IBANDTO save(IBANDTO ibanDTO) {
		return ibandao.save(ibanDTO);
	}

	public Iterable<IBANDTO> getAll() {
		return ibandao.getAll();
	}

}
