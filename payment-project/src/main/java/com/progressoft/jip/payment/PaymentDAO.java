package com.progressoft.jip.payment;

public interface PaymentDAO {

	void save(PaymentDTO paymentDTO);

	PaymentDTO getById(Integer id);

	Iterable<PaymentDTO> get(String accountNumber);

	Iterable<PaymentDTO> getAll();

}
