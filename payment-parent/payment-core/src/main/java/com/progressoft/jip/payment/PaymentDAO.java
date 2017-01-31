package com.progressoft.jip.payment;

public interface PaymentDAO {

    PaymentDTO save(PaymentDTO paymentDTO);

    PaymentDTO getById(int id);

    Iterable<PaymentDTO> get(String accountNumber);

    Iterable<PaymentDTO> getAll();

}
