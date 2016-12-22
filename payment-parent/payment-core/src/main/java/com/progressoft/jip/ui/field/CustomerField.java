package com.progressoft.jip.ui.field;

import com.progressoft.jip.payment.customer.CustomerDTO;
import com.progressoft.jip.payment.customer.CustomerDTOImpl;

public class CustomerField extends AbstractField<CustomerDTO> {

	@Override
	public AbstractField<CustomerDTO> setValue(String value) {
		CustomerDTOImpl customerDTOImpl = new CustomerDTOImpl();
		customerDTOImpl.setName(value);
		this.value = customerDTOImpl;
		return this;
	}

}
