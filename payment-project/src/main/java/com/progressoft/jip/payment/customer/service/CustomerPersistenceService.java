package com.progressoft.jip.payment.customer.service;

import com.progressoft.jip.payment.customer.CustomerDTO;

public interface CustomerPersistenceService {
	
	Iterable<CustomerDTO>getAll();

}
