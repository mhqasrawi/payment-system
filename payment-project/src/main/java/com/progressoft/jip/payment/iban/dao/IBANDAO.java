package com.progressoft.jip.payment.iban.dao;

import com.progressoft.jip.payment.iban.IBANDTO;

public interface IBANDAO {

	IBANDTO save(IBANDTO ibandto);

	IBANDTO get(String iban);

	IBANDTO getById(long id);

	Iterable<IBANDTO> getAll();

}
