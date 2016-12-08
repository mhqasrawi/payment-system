package com.progressoft.jip.payment;

public interface PaymentDAO {
	
	void save(PaymentDTO paymentDTO);
	
	void delete(Long id);
	
	PaymentDTO getById(Long id);
	
	Iterable<PaymentDTO> get(String accountNumber);
	
	Iterable<PaymentDTO> getAll();
	
	

}
